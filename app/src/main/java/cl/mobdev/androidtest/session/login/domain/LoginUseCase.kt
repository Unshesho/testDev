package cl.mobdev.androidtest.session.login.domain

import cl.mobdev.androidtest.session.login.data.LoginRepository
import cl.mobdev.androidtest.session.login.domain.model.DomainLoginResult
import cl.mobdev.androidtest.utils.Validator
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: LoginRepository) {
    suspend fun invoke(email: String, password: String) = flow<DomainLoginResult> {
        val isPasswordValid = Validator.isValidPassword(password)
        val isEmailValid = Validator.isValidEmail(email)

        if (isEmailValid && isPasswordValid) {
            repository.signUp(email, password).collect { signResult ->
                val result = DomainLoginResult(
                    signResult = signResult,
                )
                emit(result)
            }
        } else emit(
            DomainLoginResult(
                isEmailValid = isEmailValid,
                isPasswordValid = isPasswordValid
            )
        )
    }
}
