package cl.mobdev.androidtest.session.login.data.remote

import cl.mobdev.androidtest.session.login.data.remote.model.RemoteLoginStatusResponse
import cl.mobdev.androidtest.session.login.data.remote.model.RemoteSignUpParams
import cl.mobdev.androidtest.session.login.data.remote.retrofit.APIService
import cl.mobdev.androidtest.session.login.factory.SignUpFactory.makeRemoteSignUpParams
import cl.mobdev.androidtest.session.login.factory.LoginStatusResponseFactory.makeRemoteLoginStatusResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertEquals

internal class LoginRemoteImplTest {
    private val api = mockk<APIService>()
    private val remote = LoginRemoteImpl(api)

    @Test
    fun `given RemoteSignUpParams, when signUp-Success, then return RemoteSignUpResponse`() = runBlocking {
        val remoteSignUpParams = makeRemoteSignUpParams()
        val remoteLoginStatusResponse = makeRemoteLoginStatusResponse()
        val resultOk = 200

        stubSignUp(params = remoteSignUpParams, response = Response.success(remoteLoginStatusResponse))
        val result = remote.signUp(remoteSignUpParams)

        assertEquals(actual = result.message, expected = remoteLoginStatusResponse.messageResponse)
        assertEquals(actual = result.statusCode, expected = resultOk)
    }

    @Test
    fun `given RemoteSignUpParams, when signUp-Error, then return RemoteSignUpResponse`() = runBlocking {
        val remoteSignUpParams = makeRemoteSignUpParams()
        val responseBody = ResponseBody.Companion.create(null, "error")
        val resultError = 400
        val response = Response.error<RemoteLoginStatusResponse>(400, responseBody)

        stubSignUp(params = remoteSignUpParams, response = response)
        val result = remote.signUp(remoteSignUpParams)

        assertEquals(actual = result.message, expected = response.errorBody()?.toString().orEmpty())
        assertEquals(actual = result.statusCode, expected = resultError)
    }

    private fun stubSignUp(
        params: RemoteSignUpParams,
        response: Response<RemoteLoginStatusResponse>
    ) {
        coEvery { api.signUp(params) } returns response
    }
}
