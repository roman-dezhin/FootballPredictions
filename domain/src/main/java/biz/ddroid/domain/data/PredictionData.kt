package biz.ddroid.domain.data

data class PredictionData(
    val match: NewMatchData,
    val predict: PredictData,
    val friendsPredictions: List<FriendPredictionData>,
)

data class FriendPredictionData(
    val username: String,
    val predict: PredictData
)