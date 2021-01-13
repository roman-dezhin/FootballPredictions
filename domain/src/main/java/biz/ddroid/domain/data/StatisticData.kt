package biz.ddroid.domain.data

data class StatisticData(
    val name: String,
    val points: Int,
    val predictions: Int,
    val scores: Int,
    val results: Int,
    val percent: Int,
    val wins: Int
)