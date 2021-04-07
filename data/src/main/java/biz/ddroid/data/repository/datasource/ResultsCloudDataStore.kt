package biz.ddroid.data.repository.datasource

import biz.ddroid.data.cache.MatchesCache
import biz.ddroid.data.entity.PredictionEntity
import biz.ddroid.data.entity.ResultEntity
import biz.ddroid.data.net.ConnectionManager
import biz.ddroid.data.net.MatchesService
import biz.ddroid.domain.exception.NetworkConnectionException

class ResultsCloudDataStore(
    private val connectionManager: ConnectionManager,
    private val matchesService: MatchesService,
    private val matchesCache: MatchesCache
) : ResultsDataStore {

    override suspend fun getEntityList(): List<ResultEntity> =
        if (connectionManager.isNetworkAbsent()) {
            throw NetworkConnectionException()
        } else {
            val matches: List<ResultEntity>
            try {
                val matchesAsync = matchesService.getResultsAsync()
                val result = matchesAsync.await()
                if (result.isSuccessful) {
                    matches = if (result.body() != null) result.body()!! else emptyList()
                    matchesCache.put(matches)
                    matches
                } else {
                    throw Exception("Error: " + result.message() + ", code: " + result.code())
                }
            } catch (exception: Exception) {
                throw exception
            }
        }
}