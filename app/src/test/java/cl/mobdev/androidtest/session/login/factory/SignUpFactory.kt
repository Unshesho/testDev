package cl.mobdev.androidtest.session.login.factory

import cl.mobdev.androidtest.session.login.data.remote.model.RemoteSignUpParams
import cl.mobdev.androidtest.session.login.data.remote.model.RemoteSignUpResponse
import cl.mobdev.androidtest.utils.randomfactory.RandomFactory.generateRandomString

object SignUpFactory {
    fun makeRemoteSignUpParams(): RemoteSignUpParams = RemoteSignUpParams(
        token = generateRandomString()
    )

    fun makeRemoteSignUpResponse(): RemoteSignUpResponse = RemoteSignUpResponse(
        message = generateRandomString(),
        statusCode = generateRandomString().toInt()
    )
}
