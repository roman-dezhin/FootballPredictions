package biz.ddroid.data.repository.datasource

import biz.ddroid.data.cache.PredictionsCache

class PredictionsDiskDataStore(private val matchesCache: PredictionsCache) : PredictionsDataStore {
    override suspend fun getPredictionsEntityList() = matchesCache.get()
}