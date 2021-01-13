package biz.ddroid.data.entity

import com.google.gson.annotations.SerializedName

data class LoginRequestEntity(
    @SerializedName("name") val username: String,
    @SerializedName("pass") val password: String
)
