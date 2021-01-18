package biz.ddroid.domain

import biz.ddroid.domain.data.*
import biz.ddroid.domain.exception.NetworkConnectionException
import biz.ddroid.domain.exception.ServerUnavailableException
import biz.ddroid.domain.interactor.Result
import biz.ddroid.domain.interactor.TournamentResultsInteractor
import biz.ddroid.domain.interactor.TournamentResultsInteractorImpl
import biz.ddroid.domain.repository.TournamentResultsRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test

class TournamentResultsInteractorImplTest {
    
    private lateinit var interactor: TournamentResultsInteractor

    @Test
    fun testFetchSuccessWithNoResult() {
        interactor = TournamentResultsInteractorImpl(EmptyListTournamentResultsRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)

            Assert.assertThat(actual, CoreMatchers.`is`(Result.Success(emptyList())))
        }
    }

    @Test
    fun testFetchSuccessWithResults() {
        interactor = TournamentResultsInteractorImpl(OneItemListTournamentResultsRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)

            Assert.assertThat(actual, CoreMatchers.`is`(Result.Success(
                listOf(TournamentResultData(
                    TournamentData(1, "name", false),
                    emptyList()
                )))))
        }
    }

    @Test
    fun testNoConnectionCase() {
        interactor = TournamentResultsInteractorImpl(NetworkExceptionTournamentResultsRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)
            //TODO fix test
            Assert.assertThat(actual, CoreMatchers.`is`(Result.Error(NetworkConnectionException())))        }
    }

    @Test
    fun testServiceUnavailableCase() {
        interactor = TournamentResultsInteractorImpl(ServiceUnavailableExceptionTournamentResultsRepositoryImpl())
        runBlocking {
            val actual = interactor.fetch(false)
            //TODO fix test
            Assert.assertThat(actual, CoreMatchers.`is`(Result.Error(ServerUnavailableException())))
        }
    }
}

class EmptyListTournamentResultsRepositoryImpl : TournamentResultsRepository {
    override suspend fun getTournamentResults(reload: Boolean) = emptyList<TournamentResultData>()
}

class OneItemListTournamentResultsRepositoryImpl : TournamentResultsRepository {
    override suspend fun getTournamentResults(reload: Boolean) = listOf(getData())


    private fun getData() = TournamentResultData(
        TournamentData(1, "name", false),
        emptyList()
    )
}

class NetworkExceptionTournamentResultsRepositoryImpl : TournamentResultsRepository {
    override suspend fun getTournamentResults(reload: Boolean) = throw NetworkConnectionException()
}

class ServiceUnavailableExceptionTournamentResultsRepositoryImpl : TournamentResultsRepository {
    override suspend fun getTournamentResults(reload: Boolean) = throw ServerUnavailableException()
}