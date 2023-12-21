package cl.mobdev.androidtest.session.login.data.model

import cl.mobdev.androidtest.session.login.data.remote.model.RemoteSignUpResponse
import cl.mobdev.androidtest.session.login.presentation.login.LoginResult

data class SignResult(
    val response: RemoteSignUpResponse? = null,
    val state: LoginResult
)
