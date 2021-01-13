package biz.ddroid.domain.data

data class ResultData(
    val match: NewMatchData,
    val predict: PredictData,
    val friendsPredictions: List<FriendResultData>,
    val points: Int
)

data class FriendResultData(
    val username: String,
    val predict: PredictData,
    val points: Int
)