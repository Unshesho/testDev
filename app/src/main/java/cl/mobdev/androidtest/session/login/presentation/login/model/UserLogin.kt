package cl.mobdev.androidtest.session.login.presentation.login.model

data class UserLogin(
    val email: String = "",
    val password: String = "",
    val showErrorDialog: Boolean = false
)