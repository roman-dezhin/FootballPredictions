package biz.ddroid.domain.interactor

import biz.ddroid.domain.exception.NetworkConnectionException
import biz.ddroid.domain.repository.ResultsRepository
import java.lang.Exception

class ResultsInteractorImpl(private val repository: ResultsRepository) : ResultsInteractor{
    override suspend fun fetch(reload: Boolean): Status {
        return getData(reload, 3)
    }

    private suspend fun getData(reload: Boolean, retryCount: Int): Status =
        try {
            val list = repository.getResults(reload)
            if (list.isEmpty())
                Status.NO_RESULTS
            else
                Status.SUCCESS
        } catch (e: Exception) {
            e.printStackTrace()
            if (retryCount > 0) {
                getData(retryCount > 1, retryCount - 1)
            } else {
                if (e is NetworkConnectionException)
                    Status.NO_CONNECTION
                else
                    Status.SERVICE_UNAVAILABLE
            }
        }
}