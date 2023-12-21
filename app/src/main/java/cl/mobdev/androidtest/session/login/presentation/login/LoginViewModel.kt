package cl.mobdev.androidtest.session.login.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import cl.mobdev.androidtest.session.ConstantsMessages
import cl.mobdev.androidtest.session.login.data.remote.model.RemoteSignUpResponse
import cl.mobdev.androidtest.session.login.domain.LoginUseCase
import cl.mobdev.androidtest.session.login.domain.model.DomainLoginResult
import cl.mobdev.androidtest.session.login.presentation.login.events.LoginResult
import cl.mobdev.androidtest.session.login.presentation.login.events.LoginViewState
import cl.mobdev.androidtest.session.login.presentation.login.model.UserLogin
import cl.mobdev.androidtest.session.login.ui.navigation.AppScreens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _navigateToDetails = MutableStateFlow(false)
    val navigateToDetails: StateFlow<Boolean>
        get() = _navigateToDetails

    private val _viewState = MutableStateFlow(LoginViewState())
    val viewState: StateFlow<LoginViewState>
        get() = _viewState

    private var _showErrorDialog = MutableLiveData(UserLogin())

    fun loginUser(
        email: String,
        password: String,
        navController: NavController
    ) {
        viewModelScope.launch {
            loginUseCase.invoke(email, password).collect { result ->
                when (result.signResult?.state) {
                    LoginResult.Error -> showErrorDialog(password = password, email = email)

                    is LoginResult.ErrorException -> {}
                    is LoginResult.Success -> signUp(navController = navController, result = result)

                    is LoginResult.UserNotFound -> showUserNotExist(result)

                    null -> {
                        showNullState(result)
                    }
                }
            }
            updateView()
        }
    }

    private fun showErrorDialog(email: String, password: String) {
        _showErrorDialog.value =
            UserLogin(email = email, password = password, showErrorDialog = true)
        _viewState.value = LoginViewState(isLoading = false)
    }

    private fun signUp(result: DomainLoginResult, navController: NavController) {
        val response = result.signResult?.response
        when (response?.statusCode) {
            200 -> navigate(response = response, navController = navController)

            400 -> showErrorDialog()

            500 -> showErrorDialog()
        }
    }

    private fun navigate(response: RemoteSignUpResponse, navController: NavController) {
        if (response.message == ConstantsMessages.NEW_USER_CODE) {
            navController.navigate(route = AppScreens.OnBoardingCreateEvent.route)
        } else {
            navController.navigate(route = AppScreens.HomeScreen.route)
        }
    }

    private fun showErrorDialog() {
        _showErrorDialog.value =
            UserLogin(showErrorDialog = true)
        _viewState.value = LoginViewState(isLoading = false)
    }

    private fun showUserNotExist(result: DomainLoginResult) {
        _viewState.value = LoginViewState(
            isValidEmail = result.isEmailValid,
            isValidPassword = result.isPasswordValid,
            userNotExist = true
        )
    }

    private fun showNullState(result: DomainLoginResult) {
        _viewState.value = LoginViewState(
            isValidEmail = result.isEmailValid,
            isValidPassword = result.isPasswordValid
        )
    }

    private fun updateView() {
        _viewState.value = LoginViewState(isLoading = false)
    }
}
