package cl.mobdev.androidtest.session.data.model

import cl.mobdev.androidtest.session.data.model.Constants.MESSAGE
import com.google.gson.annotations.SerializedName

data class RemoteLoginStatusResponse(
    @SerializedName(MESSAGE) val messageResponse: String
)
