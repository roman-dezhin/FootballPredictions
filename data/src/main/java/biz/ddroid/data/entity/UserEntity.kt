package biz.ddroid.data.entity

import com.google.gson.annotations.SerializedName

data class UserEntity(
    @SerializedName("uid") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("mail") val email: String,
    @SerializedName("picture") val picture: String?,
    @SerializedName("field_tourwins") val tournamentsWins: TournamentsWins
)

data class TournamentsWins(
    @SerializedName("und") val tournamentsWinsList: TournamentsWinsList
)

data class TournamentsWinsList(
    @SerializedName("value") val wins: Int
)
