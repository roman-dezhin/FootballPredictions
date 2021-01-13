package biz.ddroid.domain.interactor

import biz.ddroid.domain.data.NewMatchData
import biz.ddroid.domain.exception.NetworkConnectionException
import biz.ddroid.domain.repository.NewMatchesRepository
import java.lang.Exception

class NewMatchesInteractorImpl(
    private val newMatchesRepository: NewMatchesRepository
    ) : NewMatchesInteractor {

    override suspend fun getNewMatches(reload: Boolean) = getData(reload, 3)

    private suspend fun getData(reload: Boolean, retryCount: Int): Pair<Status, List<NewMatchData>> =
        try {
            val list = newMatchesRepository.getMatches(reload)
            if (list.isEmpty())
                Pair(Status.NO_RESULTS, emptyList())
            else
                Pair(Status.SUCCESS, list)
        } catch (e: Exception) {
            e.printStackTrace()
            if (retryCount > 0) {
                getData(retryCount > 0, retryCount - 1)
            } else {
                if (e is NetworkConnectionException)
                    Pair(Status.NO_CONNECTION, emptyList())
                else
                    Pair(Status.SERVICE_UNAVAILABLE, emptyList())
            }
        }
}