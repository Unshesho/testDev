package cl.mobdev.androidtest.network.firebase

import cl.mobdev.androidtest.session.login.presentation.login.LoginResult
import cl.mobdev.androidtest.session.login.presentation.login.SignInResult
import cl.mobdev.androidtest.session.login.data.cache.SharedPreferencesLogin
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationService @Inject constructor(private val firebase: FirebaseClient) {

    suspend fun login(email: String, password: String): LoginResult {
        val firebaseAuth = FirebaseAuth.getInstance()
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            authResult.user?.getIdToken(false)?.result?.token?.let {
                SharedPreferencesLogin.saveAuthToken(it)
            }
            LoginResult.Success(authResult?.user?.isEmailVerified ?: false)
        } catch (exception: Exception) {
            if (exception is FirebaseAuthInvalidUserException) {
                LoginResult.UserNotFound(exception)
            } else {
                LoginResult.ErrorException(exception)
            }
        }
    }

    suspend fun createAccount(email: String, password: String): SignInResult? {
        return try {
            val authResult = firebase.auth.createUserWithEmailAndPassword(email, password).await()
            SignInResult.Success(authResult)
        } catch (exception: FirebaseAuthUserCollisionException) {
            SignInResult.EmailUse
        } catch (exception: Exception) {
            SignInResult.ErrorException(exception)
        }
        return null
    }


    suspend fun sendVerificationEmail() = runCatching {
        firebase.auth.currentUser?.sendEmailVerification()?.await() ?: false
    }.isSuccess

    private suspend fun verifyEmailIsVerified(): Boolean {
        firebase.auth.currentUser?.reload()?.await()
        return firebase.auth.currentUser?.isEmailVerified ?: false
    }

    private fun Result<AuthResult>.toLoginResult() = when (val result = getOrNull()) {
        null -> LoginResult.Error
        else -> {
            val userId = result.user
            checkNotNull(userId)
            LoginResult.Success(result?.user?.isEmailVerified ?: false)
        }
    }
}