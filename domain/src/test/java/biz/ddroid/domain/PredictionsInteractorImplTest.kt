package biz.ddroid.domain

import biz.ddroid.domain.data.NewMatchData
import biz.ddroid.domain.data.PredictData
import biz.ddroid.domain.data.PredictionData
import biz.ddroid.domain.exception.NetworkConnectionException
import biz.ddroid.domain.exception.ServerUnavailableException
import biz.ddroid.domain.interactor.PredictionsInteractorImpl
import biz.ddroid.domain.interactor.PredictionsInteractor
import biz.ddroid.domain.interactor.Status
import biz.ddroid.domain.repository.PredictionsRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Test

class PredictionsInteractorImplTest {

    private lateinit var interactor: PredictionsInteractor

    @Test
    fun testFetchSuccessWithNoResult() {
        interactor = PredictionsInteractorImpl(EmptyListPredictionsRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)

            Assert.assertThat(actual, `is`(Status.NO_RESULTS))
        }
    }

    @Test
    fun testFetchSuccessWithResults() {
        interactor = PredictionsInteractorImpl(OneItemListPredictionsRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)

            Assert.assertThat(actual, `is`(Status.SUCCESS))
        }
    }

    @Test
    fun testNoConnectionCase() {
        interactor = PredictionsInteractorImpl(NetworkExceptionPredictionsRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)

            Assert.assertThat(actual, `is`(Status.NO_CONNECTION))
        }
    }

    @Test
    fun testServiceUnavailableCase() {
        interactor = PredictionsInteractorImpl(ServiceUnavailableExceptionPredictionsRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)

            Assert.assertThat(actual, `is`(Status.SERVICE_UNAVAILABLE))
        }
    }
}

class EmptyListPredictionsRepositoryImpl : PredictionsRepository {
    override suspend fun getPredictions(reload: Boolean) = emptyList<PredictionData>()
}

class OneItemListPredictionsRepositoryImpl : PredictionsRepository {
    override suspend fun getPredictions(reload: Boolean) = listOf(getData())


    private fun getData() = PredictionData(
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
    )
}

class NetworkExceptionPredictionsRepositoryImpl : PredictionsRepository {
    override suspend fun getPredictions(reload: Boolean) = throw NetworkConnectionException()
}

class ServiceUnavailableExceptionPredictionsRepositoryImpl : PredictionsRepository {
    override suspend fun getPredictions(reload: Boolean) = throw ServerUnavailableException()
}