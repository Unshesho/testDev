package cl.mobdev.androidtest.session.login.data.remote.source

import cl.mobdev.androidtest.session.login.data.remote.model.RemoteSignUpParams
import cl.mobdev.androidtest.session.login.data.remote.model.RemoteSignUpResponse

interface LoginRemote {
    suspend fun signUp(params: RemoteSignUpParams): RemoteSignUpResponse
}
