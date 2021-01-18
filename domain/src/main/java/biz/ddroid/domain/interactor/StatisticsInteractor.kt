package biz.ddroid.domain.interactor

import biz.ddroid.domain.data.StatisticData

interface StatisticsInteractor {

    suspend fun fetch(reload: Boolean): Result<List<StatisticData>>
}

