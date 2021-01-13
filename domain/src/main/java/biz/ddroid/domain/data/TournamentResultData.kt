package biz.ddroid.domain.data

data class TournamentResultData(
    val tournament: TournamentData,
    val results: List<TournamentResultRowData>
)

data class TournamentResultRowData(
    val userName: String,
    val points: Int,
    val predictions: Int,
    val scores: Int,
    val results: Int,
    val winner: Boolean
)