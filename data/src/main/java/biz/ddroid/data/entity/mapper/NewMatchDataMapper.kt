package biz.ddroid.data.entity.mapper

import biz.ddroid.data.entity.NewMatchesEntity
import biz.ddroid.domain.data.NewMatchData
import java.text.ParseException
import java.text.SimpleDateFormat

import java.util.*

class NewMatchDataMapper : Mapper<List<NewMatchesEntity>, List<NewMatchData>> {

    override fun map(source: List<NewMatchesEntity>): List<NewMatchData> {
        return source.map { map(it) }
    }

    private fun map(source: NewMatchesEntity): NewMatchData {
        return NewMatchData(
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

}