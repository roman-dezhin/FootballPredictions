package biz.ddroid.data.entity

import com.google.gson.annotations.SerializedName

data class TournamentResultEntity(
    @SerializedName("tid") val id: Int,
    @SerializedName("tournament_name") val name: String,
    @SerializedName("tournament_is_finished") val isFinished: Int,
    @SerializedName("result_rows") val tournamentResultRowEntity: List<TournamentResultRowEntity>
)

data class TournamentResultRowEntity(
    @SerializedName("username") val userName: String,
    @SerializedName("points") val points: Int,
    @SerializedName("predictions") val predictions: Int,
    @SerializedName("scores") val scores: Int,
    @SerializedName("results") val results: Int,
    @SerializedName("winner") val isWinner: Int
)