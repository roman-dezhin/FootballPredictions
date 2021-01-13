package biz.ddroid.data.entity.mapper

import biz.ddroid.data.entity.FriendsPredictionsEntity
import biz.ddroid.data.entity.PredictionEntity
import biz.ddroid.domain.data.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class PredictionDataMapper : Mapper<List<PredictionEntity>, List<PredictionData>> {
    override fun map(source: List<PredictionEntity>): List<PredictionData> {
        return source.map { map(it) }
    }

    private fun map(source: PredictionEntity): PredictionData {
        return PredictionData(
            NewMatchData(
                source.id,
                convertDate(source.dateTime),
                source.tourId,
                source.tourName,
                source.stage,
                source.nameTeam1,
                source.nameTeam2,
                source.scoreTeam1,
                source.scoreTeam2,
                source.imageTeam1,
                source.imageTeam2,
                source.city
            ),
            PredictData(
                source.predictionTeam1,
                source.predictionTeam2
            ),
            getFriendsPredictions(source.friendsPredictions)
        )
    }

    private fun getFriendsPredictions(friendsPredictions: List<FriendsPredictionsEntity>): List<FriendPredictionData> {
        return friendsPredictions.map {
                FriendPredictionData(it.name, PredictData(it.predictionTeam1, it.predictionTeam2))
            }
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
}