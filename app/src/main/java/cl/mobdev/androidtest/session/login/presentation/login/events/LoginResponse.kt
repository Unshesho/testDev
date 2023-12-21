package cl.mobdev.androidtest.session.login.presentation.login.events

sealed class LoginResult {
    object Error : LoginResult()
    data class Success(val verified: Boolean) : LoginResult()
    data class UserNotFound(val exception: Exception) : LoginResult()
    data class ErrorException(val exception: Exception) : LoginResult()

}