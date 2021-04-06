package biz.ddroid.data.entity

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("sessid") val sessionId: String,
    @SerializedName("session_name") val sessionName: String,
    @SerializedName("token") val token: String,
    @SerializedName("user") val userEntity: UserEntity
)

