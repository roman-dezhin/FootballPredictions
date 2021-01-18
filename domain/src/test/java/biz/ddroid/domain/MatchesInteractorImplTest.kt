package biz.ddroid.domain

import biz.ddroid.domain.data.NewMatchData
import biz.ddroid.domain.exception.NetworkConnectionException
import biz.ddroid.domain.exception.ServerUnavailableException
import biz.ddroid.domain.interactor.NewMatchesInteractor
import biz.ddroid.domain.interactor.NewMatchesInteractorImpl
import biz.ddroid.domain.interactor.Result
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
            val actual = interactor.fetch(false)

            Assert.assertThat(actual, `is`(Result.Success(emptyList())))
        }
    }

    @Test
    fun testFetchSuccessWithResults() {
        interactor = NewMatchesInteractorImpl(OneItemListNewMatchesRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)

            Assert.assertThat(actual, `is`(Result.Success(listOf(NewMatchData(
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
            )))))
        }
    }

    @Test
    fun testNoConnectionCase() {
        interactor = NewMatchesInteractorImpl(NetworkExceptionNewMatchesRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)
            //TODO fix test
            Assert.assertThat(actual, `is`(Result.Error(NetworkConnectionException())))
        }
    }

    @Test
    fun testServiceUnavailableCase() {
        interactor = NewMatchesInteractorImpl(ServiceUnavailableExceptionNewMatchesRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)
            //TODO fix test
            Assert.assertThat(actual, `is`(Result.Error(ServerUnavailableException())))
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