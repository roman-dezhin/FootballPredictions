package biz.ddroid.data.repository.datasource

import biz.ddroid.data.cache.MatchesCache
import biz.ddroid.data.entity.PredictionEntity
import biz.ddroid.data.net.ConnectionManager
import biz.ddroid.data.net.MatchesService
import biz.ddroid.domain.exception.NetworkConnectionException

class PredictionsCloudDataStore(
    private val connectionManager: ConnectionManager,
    private val matchesService: MatchesService,
    private val matchesCache: MatchesCache
) : PredictionsDataStore {

    override suspend fun getPredictionsEntityList(): List<PredictionEntity> =
        if (connectionManager.isNetworkAbsent()) {
            throw NetworkConnectionException()
        } else {
            val matches: List<PredictionEntity>
            try {
                val matchesAsync = matchesService.getPredictionsAsync()
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