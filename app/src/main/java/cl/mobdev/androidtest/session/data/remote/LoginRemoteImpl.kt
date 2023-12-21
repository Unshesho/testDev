package cl.mobdev.androidtest.session.data.remote

import android.util.Log
import cl.mobdev.androidtest.session.data.model.RemoteSignUpParams
import cl.mobdev.androidtest.session.data.model.RemoteSignUpResponse
import cl.mobdev.androidtest.session.data.remote.retrofit.APIService
import cl.mobdev.androidtest.session.network.Networking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRemoteImpl {
    suspend fun login(token: RemoteSignUpParams): RemoteSignUpResponse {
        runCatching {
            withContext(Dispatchers.IO) {
                Networking.getRetrofit().create(APIService::class.java).login("/signUp", token)
            }
        }.fold(
            onSuccess = { response ->
                Log.d("Endpoint Info", response.code().toString())
                Log.d("Endpoint Info", response.body()?.messageResponse.orEmpty())

                return RemoteSignUpResponse(
                    statusCode = response.code(),
                    message = response.body()?.messageResponse.orEmpty()
                )
            },
            onFailure = { error ->
                Log.e("Endpoint", error.toString())
                return RemoteSignUpResponse(
                    statusCode = 400,
                    message = error.message.toString()
                )
            }
        )
    }
}