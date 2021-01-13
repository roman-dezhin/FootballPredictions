package biz.ddroid.data.entity

import com.google.gson.annotations.SerializedName

data class UserTournamentResultsEntity(
    @SerializedName("username") val userName: String,
    @SerializedName("matches") val matches: List<UserTournamentMatchesEntity>
)

data class UserTournamentMatchesEntity(
    @SerializedName("mid") val id: Int,
    @SerializedName("date") val dateTime: String,
    @SerializedName("team_home") val nameTeam1: String,
    @SerializedName("team_visitor") val nameTeam2: String,
    @SerializedName("team_home_prediction") val predictionTeam1: Int,
    @SerializedName("team_visitor_prediction") val predictionTeam2: Int,
    @SerializedName("team_home_score") val scoreTeam1: Int,
    @SerializedName("team_visitor_score") val scoreTeam2: Int,
    @SerializedName("points") val points: Int
)