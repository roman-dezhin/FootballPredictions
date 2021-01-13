package biz.ddroid.data.entity.mapper

import biz.ddroid.data.entity.StatisticEntity
import biz.ddroid.domain.data.StatisticData

class StatisticDataMapper : Mapper<List<StatisticEntity>, List<StatisticData>> {

    override fun map(source: List<StatisticEntity>): List<StatisticData> {
        return source.map { map(it) }
    }

    private fun map(source: StatisticEntity): StatisticData {
        return StatisticData(
            source.name,
            source.points,
            source.predictions,
            source.scores,
            source.results,
            source.percent,
            source.wins
        )
    }
}