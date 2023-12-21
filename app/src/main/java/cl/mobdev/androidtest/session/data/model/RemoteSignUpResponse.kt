package cl.mobdev.androidtest.session.data.model

import com.google.gson.annotations.SerializedName

data class RemoteSignUpResponse(
    @SerializedName("code") val statusCode: Int,
    @SerializedName("message") val message: String
)