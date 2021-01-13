package biz.ddroid.domain

import biz.ddroid.domain.data.NewMatchData
import biz.ddroid.domain.data.PredictData
import biz.ddroid.domain.data.ResultData
import biz.ddroid.domain.exception.NetworkConnectionException
import biz.ddroid.domain.exception.ServerUnavailableException
import biz.ddroid.domain.interactor.ResultsInteractor
import biz.ddroid.domain.interactor.ResultsInteractorImpl
import biz.ddroid.domain.interactor.Status
import biz.ddroid.domain.repository.ResultsRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test

class ResultsInteractorImplTest {
    
    private lateinit var interactor: ResultsInteractor

    @Test
    fun testFetchSuccessWithNoResult() {
        interactor = ResultsInteractorImpl(EmptyListResultsRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)

            Assert.assertThat(actual, CoreMatchers.`is`(Status.NO_RESULTS))
        }
    }

    @Test
    fun testFetchSuccessWithResults() {
        interactor = ResultsInteractorImpl(OneItemListResultsRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)

            Assert.assertThat(actual, CoreMatchers.`is`(Status.SUCCESS))
        }
    }

    @Test
    fun testNoConnectionCase() {
        interactor = ResultsInteractorImpl(NetworkExceptionResultsRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)

            Assert.assertThat(actual, CoreMatchers.`is`(Status.NO_CONNECTION))
        }
    }

    @Test
    fun testServiceUnavailableCase() {
        interactor = ResultsInteractorImpl(ServiceUnavailableExceptionResultsRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)

            Assert.assertThat(actual, CoreMatchers.`is`(Status.SERVICE_UNAVAILABLE))
        }
    }
}

class EmptyListResultsRepositoryImpl : ResultsRepository {
    override suspend fun getResults(reload: Boolean) = emptyList<ResultData>()
}

class OneItemListResultsRepositoryImpl : ResultsRepository {
    override suspend fun getResults(reload: Boolean) = listOf(getData())


    private fun getData() = ResultData(
        NewMatchData(123,
            "15-09-2018 at 00:00",
            123,
            "Tournament name",
            "matchday 1",
            "team1 name",
            "team2 name",
            5,
            0,
            "team1 imageUrl",
            "team2 imageUrl",
            "city"),
        PredictData(1,2),
        emptyList(),
        5
    )
}

class NetworkExceptionResultsRepositoryImpl : ResultsRepository {
    override suspend fun getResults(reload: Boolean) = throw NetworkConnectionException()
}

class ServiceUnavailableExceptionResultsRepositoryImpl : ResultsRepository {
    override suspend fun getResults(reload: Boolean) = throw ServerUnavailableException()
}