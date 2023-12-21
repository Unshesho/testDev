package cl.mobdev.androidtest.session.data.model

import com.google.gson.annotations.SerializedName

data class RemoteSignUpParams(
    @SerializedName("token") val token: String
)