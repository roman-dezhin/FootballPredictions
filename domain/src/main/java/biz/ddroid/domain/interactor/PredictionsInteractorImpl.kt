package biz.ddroid.domain.interactor

import biz.ddroid.domain.data.PredictionData
import biz.ddroid.domain.exception.NetworkConnectionException
import biz.ddroid.domain.repository.PredictionsRepository
import java.lang.Exception

class PredictionsInteractorImpl(
    private val repository: PredictionsRepository
    ) : PredictionsInteractor{

    override suspend fun fetch(reload: Boolean): Result<List<PredictionData>> =
        try {
            Result.Success(repository.getPredictions(reload))
        } catch (e: Exception) {
            if (e is NetworkConnectionException)
                Result.Error(NetworkConnectionException())
            else
                Result.Error(e)
        }
}