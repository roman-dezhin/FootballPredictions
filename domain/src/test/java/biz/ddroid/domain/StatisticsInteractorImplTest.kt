package biz.ddroid.domain

import biz.ddroid.domain.data.StatisticData
import biz.ddroid.domain.exception.NetworkConnectionException
import biz.ddroid.domain.exception.ServerUnavailableException
import biz.ddroid.domain.interactor.Result
import biz.ddroid.domain.interactor.StatisticsInteractor
import biz.ddroid.domain.interactor.StatisticsInteractorImpl
import biz.ddroid.domain.repository.StatisticsRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test

class StatisticsInteractorImplTest {

    private lateinit var interactor: StatisticsInteractor

    @Test
    fun testFetchSuccessWithNoResult() {
        interactor = StatisticsInteractorImpl(EmptyListStatisticsRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)

            Assert.assertThat(actual, CoreMatchers.`is`(Result.Success(emptyList())))
        }
    }

    @Test
    fun testFetchSuccessWithResults() {
        interactor = StatisticsInteractorImpl(OneItemListStatisticsRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)

            Assert.assertThat(actual, CoreMatchers.`is`(Result.Success(
                listOf(StatisticData(
                    "name",
                    123,
                    123,
                    123,
                    123,
                    123,
                    123
                )))))
        }
    }

    @Test
    fun testNoConnectionCase() {
        interactor = StatisticsInteractorImpl(NetworkExceptionStatisticsRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)
            //TODO fix test
            Assert.assertThat(actual, CoreMatchers.`is`(Result.Error(NetworkConnectionException())))
        }
    }

    @Test
    fun testServiceUnavailableCase() {
        interactor = StatisticsInteractorImpl(ServiceUnavailableExceptionStatisticsRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)
            //TODO fix test
            Assert.assertThat(actual, CoreMatchers.`is`(Result.Error(ServerUnavailableException())))
        }
    }
}

class EmptyListStatisticsRepositoryImpl : StatisticsRepository {
    override suspend fun getStatistics(reload: Boolean) = emptyList<StatisticData>()
}

class OneItemListStatisticsRepositoryImpl : StatisticsRepository {
    override suspend fun getStatistics(reload: Boolean) = listOf(getData())


    private fun getData() = StatisticData(
        "name",
        123,
    123,
        123,
        123,
        123,
        123
    )
}

class NetworkExceptionStatisticsRepositoryImpl : StatisticsRepository {
    override suspend fun getStatistics(reload: Boolean) = throw NetworkConnectionException()
}

class ServiceUnavailableExceptionStatisticsRepositoryImpl : StatisticsRepository {
    override suspend fun getStatistics(reload: Boolean) = throw ServerUnavailableException()
}