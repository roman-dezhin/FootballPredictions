package biz.ddroid.data.entity

import com.google.gson.annotations.SerializedName

data class UserStatistic(
    @SerializedName("predictions_count") val totalPredictionsCount: Int,
    @SerializedName("points") val totalPoints: Int
)
