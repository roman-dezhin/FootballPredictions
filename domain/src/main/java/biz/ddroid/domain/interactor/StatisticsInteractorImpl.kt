package biz.ddroid.domain.interactor

import biz.ddroid.domain.data.StatisticData
import biz.ddroid.domain.exception.NetworkConnectionException
import biz.ddroid.domain.exception.ServerUnavailableException
import biz.ddroid.domain.repository.StatisticsRepository
import java.lang.Exception

class StatisticsInteractorImpl(
    private val repository: StatisticsRepository
    ) : StatisticsInteractor{

    override suspend fun fetch(reload: Boolean): Result<List<StatisticData>> =
        try {
            Result.Success(repository.getStatistics(reload))
        } catch (e: Exception) {
            if (e is NetworkConnectionException)
                Result.Error(NetworkConnectionException())
            else
                Result.Error(e)
        }
}