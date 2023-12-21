package cl.mobdev.androidtest.session.login.factory

import cl.mobdev.androidtest.session.login.data.remote.model.RemoteLoginStatusResponse
import cl.mobdev.androidtest.utils.randomfactory.RandomFactory.generateRandomString

object LoginStatusResponseFactory {
    fun makeRemoteLoginStatusResponse(): RemoteLoginStatusResponse = RemoteLoginStatusResponse(
        messageResponse = generateRandomString()
    )
}