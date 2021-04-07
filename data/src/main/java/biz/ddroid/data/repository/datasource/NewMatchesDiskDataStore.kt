package biz.ddroid.data.repository.datasource

import biz.ddroid.data.cache.NewMatchesCache

class NewMatchesDiskDataStore(private val matchesCache: NewMatchesCache) : NewMatchesDataStore {
    override suspend fun getNewMatchesEntityList() = matchesCache.get()
}