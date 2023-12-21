package cl.mobdev.androidtest.session.login.data.remote

import cl.mobdev.androidtest.session.login.data.remote.model.RemoteSignUpParams
import cl.mobdev.androidtest.session.login.data.remote.model.RemoteSignUpResponse
import cl.mobdev.androidtest.session.login.data.remote.retrofit.APIService
import cl.mobdev.androidtest.session.login.data.remote.source.LoginRemote
import javax.inject.Inject

class LoginRemoteImpl @Inject constructor(private val api: APIService) : LoginRemote {
    override suspend fun signUp(params: RemoteSignUpParams): RemoteSignUpResponse {
        val response = api.signUp(params)
        return if (response.isSuccessful) RemoteSignUpResponse(
            statusCode = response.code(),
            message = response.body()?.messageResponse.orEmpty()
        )
        else RemoteSignUpResponse(
            statusCode = response.code(),
            message = response.errorBody()?.toString().orEmpty()
        )
    }
}
