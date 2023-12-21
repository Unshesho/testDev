package cl.mobdev.androidtest.session.login.data

import cl.mobdev.androidtest.network.firebase.AuthenticationService
import cl.mobdev.androidtest.session.login.data.cache.SharedPreferencesLogin
import cl.mobdev.androidtest.session.login.data.model.SignResult
import cl.mobdev.androidtest.session.login.data.remote.model.RemoteSignUpParams
import cl.mobdev.androidtest.session.login.data.remote.source.LoginRemote
import cl.mobdev.androidtest.session.login.presentation.login.LoginResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val remote: LoginRemote
) {
    suspend fun signUp(email: String, password: String) = flow<SignResult> {
        when (val result = authenticationService.login(email, password)) {
            is LoginResult.Success -> {
                setCurrentFirebaseUser { params ->
                    suspend {
                        val response = remote.signUp(params)
                        emit(SignResult(response = response, state = result))
                    }
                }
            }
            is LoginResult.Error -> emit(SignResult(state = result))
            is LoginResult.ErrorException -> emit(SignResult(state = result))
            is LoginResult.UserNotFound -> emit(SignResult(state = result))
        }

    }

    private fun setCurrentFirebaseUser(event: (RemoteSignUpParams) -> Unit) {
        val actualUser = FirebaseAuth.getInstance().currentUser
        actualUser?.getIdToken(true)?.addOnSuccessListener { result ->
            result.token?.let { SharedPreferencesLogin.saveAuthToken(it) }
            val idToken = RemoteSignUpParams(result.token.orEmpty())
            event(idToken)
        }
    }
}
