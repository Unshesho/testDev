package cl.mobdev.androidtest.session.login.data.remote.model

import cl.mobdev.androidtest.session.login.data.remote.model.Constants.MESSAGE
import com.google.gson.annotations.SerializedName

data class RemoteLoginStatusResponse(
    @SerializedName(MESSAGE) val messageResponse: String
)
