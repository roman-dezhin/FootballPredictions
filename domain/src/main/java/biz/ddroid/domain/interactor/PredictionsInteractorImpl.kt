package biz.ddroid.domain.interactor

import biz.ddroid.domain.data.PredictionData
import biz.ddroid.domain.exception.NetworkConnectionException
import biz.ddroid.domain.exception.ServerUnavailableException
import biz.ddroid.domain.repository.PredictionsRepository
import java.lang.Exception

class PredictionsInteractorImpl(
    private val repository: PredictionsRepository
    ) : PredictionsInteractor{

    override suspend fun fetch(reload: Boolean) = getData(reload, 3)

    private suspend fun getData(reload: Boolean, retryCount: Int): Result<List<PredictionData>> =
        try {
            Result.Success(repository.getPredictions(reload))
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