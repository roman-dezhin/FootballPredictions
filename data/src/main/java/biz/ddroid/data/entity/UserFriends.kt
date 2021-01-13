package biz.ddroid.data.entity

import com.google.gson.annotations.SerializedName

data class UserFriends(
    @SerializedName("uid") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("avatar") val avatar: String
)
