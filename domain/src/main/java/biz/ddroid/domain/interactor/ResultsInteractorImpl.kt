package biz.ddroid.domain.interactor

import biz.ddroid.domain.data.ResultData
import biz.ddroid.domain.exception.NetworkConnectionException
import biz.ddroid.domain.exception.ServerUnavailableException
import biz.ddroid.domain.repository.ResultsRepository
import java.lang.Exception

class ResultsInteractorImpl(
    private val repository: ResultsRepository
    ) : ResultsInteractor{

    override suspend fun fetch(reload: Boolean): Result<List<ResultData>> =
        try {
            Result.Success(repository.getResults(reload))
        } catch (e: Exception) {
            if (e is NetworkConnectionException)
                Result.Error(NetworkConnectionException())
            else
                Result.Error(e)
        }
}