package biz.ddroid.domain.data

data class NewMatchData(
    val id: Int,
    val dateTime: String,
    val tourId: Int,
    val tourName: String,
    val stage: String,
    val team1: String,
    val team2: String,
    val scoreTeam1: Int,
    val scoreTeam2: Int,
    val imageTeam1: String,
    val imageTeam2: String,
    val city: String
)