package biz.ddroid.domain.repository

import biz.ddroid.domain.data.StatisticData

interface StatisticsRepository {
    suspend fun getStatistics(reload: Boolean = false): List<StatisticData>
}