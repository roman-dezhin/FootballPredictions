package biz.ddroid.data.entity

import com.google.gson.annotations.SerializedName

data class StatisticEntity(
    @SerializedName("username") val name: String,
    @SerializedName("points") val points: Int,
    @SerializedName("predictions") val predictions: Int,
    @SerializedName("scores") val scores: Int,
    @SerializedName("results") val results: Int,
    @SerializedName("percents") val percent: Int,
    @SerializedName("wins") val wins: Int
)
