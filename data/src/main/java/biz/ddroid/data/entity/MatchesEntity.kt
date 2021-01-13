package biz.ddroid.data.entity

import com.google.gson.annotations.SerializedName

sealed class MatchesEntity

data class NewMatchesEntity(
    @SerializedName("mid") val id: Int,
    @SerializedName("date") val dateTime: String,
    @SerializedName("tid") val tourId: Int,
    @SerializedName("tournament_name") val tourName: String,
    @SerializedName("stage") val stage: String,
    @SerializedName("team_home") val nameTeam1: String,
    @SerializedName("team_visitor") val nameTeam2: String,
    val scoreTeam1: Int = -1,
    val scoreTeam2: Int = -1,
    @SerializedName("team_home_icon") val imageTeam1: String,
    @SerializedName("team_visitor_icon") val imageTeam2: String,
    @SerializedName("city") val city: String,
    @SerializedName("predictions_count") val predictions_count: Int
) : MatchesEntity()

data class PredictionEntity(
    @SerializedName("mid") val id: Int,
    @SerializedName("date") val dateTime: String,
    @SerializedName("tid") val tourId: Int,
    @SerializedName("tournament_name") val tourName: String,
    @SerializedName("stage") val stage: String,
    @SerializedName("team_home") val nameTeam1: String,
    @SerializedName("team_visitor") val nameTeam2: String,
    @SerializedName("team_home_prediction") val predictionTeam1: Int,
    @SerializedName("team_visitor_prediction") val predictionTeam2: Int,
    val scoreTeam1: Int = -1,
    val scoreTeam2: Int = -1,
    @SerializedName("team_home_icon") val imageTeam1: String,
    @SerializedName("team_visitor_icon") val imageTeam2: String,
    @SerializedName("city") val city: String,
    @SerializedName("friends_predictions") val friendsPredictions: List<FriendsPredictionsEntity>
) : MatchesEntity()

data class FriendsPredictionsEntity(
    @SerializedName("user_name") val name: String,
    @SerializedName("team_home_prediction") val predictionTeam1: Int,
    @SerializedName("team_visitor_prediction") val predictionTeam2: Int
)

data class ResultEntity(
    @SerializedName("mid") val id: Int,
    @SerializedName("date") val dateTime: String,
    @SerializedName("tid") val tourId: Int,
    @SerializedName("tournament_name") val tourName: String,
    @SerializedName("stage") val stage: String,
    @SerializedName("team_home") val nameTeam1: String,
    @SerializedName("team_visitor") val nameTeam2: String,
    @SerializedName("team_home_prediction") val predictionTeam1: Int,
    @SerializedName("team_visitor_prediction") val predictionTeam2: Int,
    @SerializedName("team_home_score") val scoreTeam1: Int,
    @SerializedName("team_visitor_score") val scoreTeam2: Int,
    @SerializedName("team_home_icon") val imageTeam1: String,
    @SerializedName("team_visitor_icon") val imageTeam2: String,
    @SerializedName("city") val city: String,
    @SerializedName("friends_predictions_and_points") val friendsPredictionsWithPoints: List<FriendsPredictionsWithPointsEntity>,
    @SerializedName("points") val points: Int
) : MatchesEntity()

data class FriendsPredictionsWithPointsEntity(
    @SerializedName("user_name") val name: String,
    @SerializedName("team_home_prediction") val predictionTeam1: Int,
    @SerializedName("team_visitor_prediction") val predictionTeam2: Int,
    @SerializedName("points") val points: Int
)