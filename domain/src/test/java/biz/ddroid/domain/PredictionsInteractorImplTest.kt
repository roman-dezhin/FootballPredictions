package biz.ddroid.domain

import biz.ddroid.domain.data.NewMatchData
import biz.ddroid.domain.data.PredictData
import biz.ddroid.domain.data.PredictionData
import biz.ddroid.domain.exception.NetworkConnectionException
import biz.ddroid.domain.exception.ServerUnavailableException
import biz.ddroid.domain.interactor.PredictionsInteractorImpl
import biz.ddroid.domain.interactor.PredictionsInteractor
import biz.ddroid.domain.interactor.Result
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

            Assert.assertThat(actual, `is`(Result.Success(emptyList())))
        }
    }

    @Test
    fun testFetchSuccessWithResults() {
        interactor = PredictionsInteractorImpl(OneItemListPredictionsRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)

            Assert.assertThat(actual, `is`(Result.Success(
                listOf(PredictionData(
                    NewMatchData(
                        123,
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
                        "city"
                    ),
                    PredictData(1,2),
                    emptyList(),
            )))))
        }
    }

    @Test
    fun testNoConnectionCase() {
        interactor = PredictionsInteractorImpl(NetworkExceptionPredictionsRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)
            //TODO fix test
            Assert.assertThat(actual, `is`(Result.Error(NetworkConnectionException())))
        }
    }

    @Test
    fun testServiceUnavailableCase() {
        interactor = PredictionsInteractorImpl(ServiceUnavailableExceptionPredictionsRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)
            //TODO fix test
            Assert.assertThat(actual, `is`(Result.Error(ServerUnavailableException())))
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