package cl.mobdev.androidtest.session.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import cl.mobdev.androidtest.session.ui.navigation.AppScreens
import cl.mobdev.androidtest.session.ConstantsMessages
import cl.mobdev.androidtest.session.Event
import cl.mobdev.androidtest.session.domain.LoginUseCase
import cl.mobdev.androidtest.session.network.Networking
import cl.mobdev.androidtest.session.data.model.RemoteSignUpParams
import cl.mobdev.androidtest.session.data.cache.SharedPreferencesLogin
import cl.mobdev.androidtest.session.presentation.login.model.UserLogin
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    val loginUseCase: LoginUseCase
) : ViewModel() {
    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    private val _navigateToDetails = MutableStateFlow(false)
    val navigateToDetails: StateFlow<Boolean>
        get() = _navigateToDetails

    private val _navigateToVerifyAccount = MutableLiveData<Event<Boolean>>()
    val navigateToVerifyAccount: LiveData<Event<Boolean>>
        get() = _navigateToVerifyAccount

    private val _viewState = MutableStateFlow(LoginViewState())
    val viewState: StateFlow<LoginViewState>
        get() = _viewState

    private var _showErrorDialog = MutableLiveData(UserLogin())
    val showErrorDialog: LiveData<UserLogin>
        get() = _showErrorDialog


    fun onLoginSelected(
        email: String,
        password: String,
        navController: NavController,
        trimmedEmail: String
    ) {
        if (isValidEmail(email,trimmedEmail ) && isValidPassword(password)) {
            loginUser(email, password, trimmedEmail, navController)
        } else {
            onFieldsChanged(email, password, trimmedEmail)
        }
    }

    fun loginUser(email: String, password: String, trimmedEmail: String, navController: NavController) {
        viewModelScope.launch {
            _viewState.value = LoginViewState(isLoading = true)
            when (val result = loginUseCase(email, password)) {
                LoginResult.Error -> {
                    _showErrorDialog.value =
                        UserLogin(email = email, password = password, showErrorDialog = true)
                    _viewState.value = LoginViewState(isLoading = false)
                }
                is LoginResult.Success -> {
                    setCurrentFirebaseUser(navController)

                }
                is LoginResult.UserNotFound -> {
                    _viewState.value = LoginViewState(
                        isValidEmail = isValidEmail(email,trimmedEmail),
                        isValidPassword = isValidPassword(password),
                        userNotExist = true
                    )
                }
                else -> {}
            }
            _viewState.value = LoginViewState(isLoading = false)
        }
    }

    fun onFieldsChanged(email: String, password: String, trimmedEmail: String) {
        _viewState.value = LoginViewState(
            isValidEmail = isValidEmail(email, trimmedEmail),
            isValidPassword = isValidPassword(password),
        )
    }

    fun isValidEmail(email: String?, trimmedEmail: String?): Boolean {
        if (email == null || trimmedEmail == null) {
            return false
        }
        val MIN_PASSWORD_LENGTH = 8
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")

        val isValidEmailAddress = emailPattern.matches(trimmedEmail)
        val isEmailUnchanged = trimmedEmail == email
        val isNotEmpty = email.isNotEmpty()
        val hasValidLength = email.length >= MIN_PASSWORD_LENGTH

        return (isValidEmailAddress && isEmailUnchanged) || (isNotEmpty && hasValidLength)
    }

    private fun isValidPassword(password: String): Boolean {
        val minLength = 8 // Longitud mínima de la contraseña
        val upperCasePattern = Regex("[A-Z]") // Al menos una letra mayúscula
        val lowerCasePattern = Regex("[a-z]") // Al menos una letra minúscula
        val digitPattern = Regex("[0-9]") // Al menos un dígito

        // Verifica la longitud mínima
        if (password.length < minLength) {
            return false
        }

        // Verifica al menos una letra mayúscula
        if (!upperCasePattern.containsMatchIn(password)) {
            return false
        }
        // Verifica al menos una letra minúscula
        if (!lowerCasePattern.containsMatchIn(password)) {
            return false
        }
        // Verifica al menos un dígito
        if (!digitPattern.containsMatchIn(password)) {
            return false
        }
        // Si pasa todas las comprobaciones, la contraseña es válida
        return true
    }


    private fun setCurrentFirebaseUser(navController: NavController) {
        val actualUser = FirebaseAuth.getInstance().currentUser
        actualUser?.getIdToken(true)?.addOnSuccessListener { result ->
            result.token?.let { SharedPreferencesLogin.saveAuthToken(it) }
            val idToken = RemoteSignUpParams(result.token.orEmpty())
            signUpTokenValidation(token = idToken, navController)
        }
    }

    private fun signUpTokenValidation(token: RemoteSignUpParams, navController: NavController) {
        viewModelScope.launch {
            val responseSignUp = Networking.postLoginStatus(token)

            when (responseSignUp.statusCode) {
                200 -> {
                    if (responseSignUp.message == ConstantsMessages.NEW_USER_CODE) {
                        navController.navigate(route = AppScreens.OnBoardingCreateEvent.route)
                    } else {
                        navController.navigate(route = AppScreens.HomeScreen.route)
                    }
                }
                400 -> {
                    _showErrorDialog.value =
                        UserLogin(showErrorDialog = true)
                    _viewState.value = LoginViewState(isLoading = false)
                }
                500 -> {
                    _showErrorDialog.value =
                        UserLogin(showErrorDialog = true)
                    _viewState.value = LoginViewState(isLoading = false)
                }
            }
        }
    }

}