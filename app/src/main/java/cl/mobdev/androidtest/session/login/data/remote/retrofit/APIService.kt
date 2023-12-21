package cl.mobdev.androidtest.session.login.data.remote.retrofit

import cl.mobdev.androidtest.session.login.data.remote.model.RemoteLoginStatusResponse
import cl.mobdev.androidtest.session.login.data.remote.model.RemoteSignUpParams
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {
    @POST("/signUp")
    suspend fun signUp(
        @Body token: RemoteSignUpParams
    ): Response<RemoteLoginStatusResponse>
}
