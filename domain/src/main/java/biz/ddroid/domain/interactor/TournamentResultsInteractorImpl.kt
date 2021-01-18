package biz.ddroid.domain.interactor

import biz.ddroid.domain.data.TournamentResultData
import biz.ddroid.domain.exception.NetworkConnectionException
import biz.ddroid.domain.exception.ServerUnavailableException
import biz.ddroid.domain.repository.TournamentResultsRepository
import java.lang.Exception

class TournamentResultsInteractorImpl(
    private val tournamentResultsRepository: TournamentResultsRepository
    ) : TournamentResultsInteractor{

    override suspend fun fetch(reload: Boolean) = getData(reload, 3)

    private suspend fun getData(reload: Boolean, retryCount: Int): Result<List<TournamentResultData>> =
        try {
            Result.Success(tournamentResultsRepository.getTournamentResults(reload))
        } catch (e: Exception) {
            e.printStackTrace()
            if (retryCount > 0) {
                getData(retryCount > 1, retryCount - 1)
            } else {
                if (e is NetworkConnectionException)
                    Result.Error(NetworkConnectionException())
                else
                    Result.Error(ServerUnavailableException())
            }
        }
}