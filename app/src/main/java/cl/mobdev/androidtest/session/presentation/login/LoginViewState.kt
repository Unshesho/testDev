package cl.mobdev.androidtest.session.presentation.login

data class LoginViewState(
    val isLoading: Boolean = false,
    val isValidEmail: Boolean = true,
    val isValidPassword: Boolean = true,
    val userNotExist: Boolean = false,
    val isValidMail: Boolean = true
)