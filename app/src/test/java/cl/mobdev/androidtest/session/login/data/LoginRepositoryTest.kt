package cl.mobdev.androidtest.session.login.data

import cl.mobdev.androidtest.network.firebase.AuthenticationService
import cl.mobdev.androidtest.session.login.data.remote.model.RemoteSignUpParams
import cl.mobdev.androidtest.session.login.data.remote.model.RemoteSignUpResponse
import cl.mobdev.androidtest.session.login.data.remote.source.LoginRemote
import cl.mobdev.androidtest.session.login.factory.SignUpFactory.makeRemoteSignUpParams
import cl.mobdev.androidtest.session.login.factory.SignUpFactory.makeRemoteSignUpResponse
import cl.mobdev.androidtest.session.login.presentation.login.events.LoginResult
import cl.mobdev.androidtest.utils.randomfactory.RandomFactory.generateRandomString
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class LoginRepositoryTest {
    private val authenticationService = mockk<AuthenticationService>()
    private val remote = mockk<LoginRemote>()
    private val repository =
        LoginRepository(
            authenticationService = authenticationService,
            remote = remote
        )

    @Test
    fun `given success, when signUp, then emit Response`() = runBlocking {
        val email = generateRandomString()
        val password = generateRandomString()
        val loginResult = LoginResult.Success(true)
        val signUpParams = makeRemoteSignUpParams()
        val signUpResponse = makeRemoteSignUpResponse()

        stubAuthenticationService(email = email, password = password, response = loginResult)
        stubSignUp(params = signUpParams, response = signUpResponse)
        //TODO: stub de setCurrentFirebaseUser y todas las cositas que tiene dentro

        val result = repository.signUp(email = email, password = password)
    }

    private fun stubAuthenticationService(email: String, password: String, response: LoginResult) {
        coEvery { authenticationService.login(email = email, password = password) } returns response
    }

    private fun stubSignUp(params: RemoteSignUpParams, response: RemoteSignUpResponse) {
        coEvery { remote.signUp(params) } returns response
    }
}
