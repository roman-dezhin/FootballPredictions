package biz.ddroid.domain.interactor

import biz.ddroid.domain.data.NewMatchData
import biz.ddroid.domain.exception.NetworkConnectionException
import biz.ddroid.domain.exception.ServerUnavailableException
import biz.ddroid.domain.repository.NewMatchesRepository
import java.lang.Exception

class NewMatchesInteractorImpl(
    private val newMatchesRepository: NewMatchesRepository
    ) : NewMatchesInteractor {

    override suspend fun fetch(reload: Boolean) = getData(reload, 3)

    private suspend fun getData(reload: Boolean, retryCount: Int): Result<List<NewMatchData>> =
        try {
            Result.Success(newMatchesRepository.getMatches(reload))
        } catch (e: Exception) {
            e.printStackTrace()
            if (retryCount > 0) {
                getData(retryCount > 0, retryCount - 1)
            } else {
                if (e is NetworkConnectionException)
                    Result.Error(NetworkConnectionException())
                else
                    Result.Error(ServerUnavailableException())
            }
        }

}