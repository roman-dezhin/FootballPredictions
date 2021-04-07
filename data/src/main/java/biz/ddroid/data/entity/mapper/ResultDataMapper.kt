package biz.ddroid.data.entity.mapper

import biz.ddroid.data.entity.FriendsPredictionsWithPointsEntity
import biz.ddroid.data.entity.ResultEntity
import biz.ddroid.domain.data.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ResultDataMapper : Mapper<List<ResultEntity>, List<ResultData>> {
    override fun map(source: List<ResultEntity>): List<ResultData> {
        return source.map { map(it) }
    }

    private fun map(source: ResultEntity): ResultData {
        return ResultData(
            NewMatchData(
                source.id,
                convertDate(source.dateTime),
                source.tourId,
                source.tourName,
                source?.stage ?: "",
                source.nameTeam1,
                source.nameTeam2,
                source.scoreTeam1,
                source.scoreTeam2,
                source?.imageTeam1 ?: "",
                source?.imageTeam2 ?: "",
                source?.city ?: ""
            ),
            PredictData(
                source.predictionTeam1,
                source.predictionTeam2
            ),
            getFriendsResults(source.friendsPredictionsWithPoints),
            source.points
        )
    }

    private fun convertDate(dateTime: String): String {
        val dateTimePattern = "HH:mm dd.MM.yy"

        val df = SimpleDateFormat(dateTimePattern, Locale.getDefault())
        df.timeZone = TimeZone.getTimeZone("UTC")
        var date: Date? = null
        try {
            date = df.parse(dateTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        df.timeZone = TimeZone.getDefault()
        return df.format(date!!)
    }

    private fun getFriendsResults(friendsResults: List<FriendsPredictionsWithPointsEntity>): List<FriendResultData> {
        return friendsResults.map {
            FriendResultData(it.name, PredictData(it.predictionTeam1, it.predictionTeam2), it.points)
        }
    }
}