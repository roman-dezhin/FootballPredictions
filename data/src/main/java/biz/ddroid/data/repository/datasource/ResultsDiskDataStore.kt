package biz.ddroid.data.repository.datasource

import biz.ddroid.data.cache.ResultsCache

class ResultsDiskDataStore(private val matchesCache: ResultsCache) : ResultsDataStore {
    override suspend fun getEntityList() = matchesCache.get()
}