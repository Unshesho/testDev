package cl.mobdev.androidtest.session.login.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteSignUpParams(
    @SerializedName("token") val token: String
)