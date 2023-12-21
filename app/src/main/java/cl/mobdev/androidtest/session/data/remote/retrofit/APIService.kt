package cl.mobdev.androidtest.session.data.remote.retrofit

import cl.mobdev.androidtest.session.data.model.RemoteLoginStatusResponse
import cl.mobdev.androidtest.session.data.model.RemoteSignUpParams
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface APIService {
    @POST
    suspend fun login(
        @Url url: String,
        @Body token: RemoteSignUpParams
    ): Response<RemoteLoginStatusResponse>
}
