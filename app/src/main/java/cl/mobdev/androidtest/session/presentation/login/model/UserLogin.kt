package cl.mobdev.androidtest.session.presentation.login.model

data class UserLogin(
    val email: String = "",
    val password: String = "",
    val showErrorDialog: Boolean = false
)