package biz.ddroid.domain

import biz.ddroid.domain.data.NewMatchData
import biz.ddroid.domain.exception.NetworkConnectionException
import biz.ddroid.domain.exception.ServerUnavailableException
import biz.ddroid.domain.interactor.NewMatchesInteractor
import biz.ddroid.domain.interactor.NewMatchesInteractorImpl
import biz.ddroid.domain.interactor.Status
import biz.ddroid.domain.repository.NewMatchesRepository
import org.junit.Assert
import org.junit.Test
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`

class MatchesInteractorImplTest {

    private lateinit var interactor: NewMatchesInteractor

    @Test
    fun testFetchSuccessWithNoResults() {
        interactor = NewMatchesInteractorImpl(EmptyListNewMatchesRepositoryImpl())
        runBlocking {
            val (actual, list) = interactor.getNewMatches(false)

            Assert.assertThat(actual, `is`(Status.NO_RESULTS))
        }
    }

    @Test
    fun testFetchSuccessWithResults() {
        interactor = NewMatchesInteractorImpl(OneItemListNewMatchesRepositoryImpl())
        runBlocking {
            val (actual, list) = interactor.getNewMatches(false)

            Assert.assertThat(actual, `is`(Status.SUCCESS))
        }
    }

    @Test
    fun testNoConnectionCase() {
        interactor = NewMatchesInteractorImpl(NetworkExceptionNewMatchesRepositoryImpl())
        runBlocking {
            val (actual, list) = interactor.getNewMatches(false)

            Assert.assertThat(actual, `is`(Status.NO_CONNECTION))
        }
    }

    @Test
    fun testServiceUnavailableCase() {
        interactor = NewMatchesInteractorImpl(ServiceUnavailableExceptionNewMatchesRepositoryImpl())
        runBlocking {
            val (actual, list) = interactor.getNewMatches(false)

            Assert.assertThat(actual, `is`(Status.SERVICE_UNAVAILABLE))
        }
    }
}

class EmptyListNewMatchesRepositoryImpl : NewMatchesRepository {
    override suspend fun getMatches(reload: Boolean) = emptyList<NewMatchData>()
}

class OneItemListNewMatchesRepositoryImpl : NewMatchesRepository {
    override suspend fun getMatches(reload: Boolean) = listOf(getData())


    private fun getData() = NewMatchData(
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
    )
}

class NetworkExceptionNewMatchesRepositoryImpl : NewMatchesRepository {
    override suspend fun getMatches(reload: Boolean) = throw NetworkConnectionException()
}

class ServiceUnavailableExceptionNewMatchesRepositoryImpl : NewMatchesRepository {
    override suspend fun getMatches(reload: Boolean) = throw ServerUnavailableException()
}