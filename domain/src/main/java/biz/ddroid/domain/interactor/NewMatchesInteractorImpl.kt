package biz.ddroid.domain.interactor

import biz.ddroid.domain.data.NewMatchData
import biz.ddroid.domain.exception.NetworkConnectionException
import biz.ddroid.domain.exception.ServerUnavailableException
import biz.ddroid.domain.repository.NewMatchesRepository
import java.lang.Exception

class NewMatchesInteractorImpl(
    private val newMatchesRepository: NewMatchesRepository
    ) : NewMatchesInteractor {

    override suspend fun fetch(reload: Boolean): Result<List<NewMatchData>> =
        try {
            Result.Success(newMatchesRepository.getMatches(reload))
        } catch (e: Exception) {
            if (e is NetworkConnectionException)
                Result.Error(NetworkConnectionException())
            else
                Result.Error(e)
        }
}