package cl.mobdev.androidtest.session.domain

import cl.mobdev.androidtest.session.network.firebase.AuthenticationService
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authenticationService: AuthenticationService) {
    suspend operator fun invoke(email: String, password: String) =
        authenticationService.login(email, password)
}