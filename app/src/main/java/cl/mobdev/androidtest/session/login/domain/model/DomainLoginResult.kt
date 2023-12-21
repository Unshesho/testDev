package cl.mobdev.androidtest.session.login.domain.model

import cl.mobdev.androidtest.session.login.data.model.SignResult

data class DomainLoginResult(
    val signResult: SignResult? = null,
    val isPasswordValid: Boolean = true,
    val isEmailValid: Boolean = true
)