package biz.ddroid.data.repository.datasource

import biz.ddroid.data.cache.NewMatchesCache

class NewMatchesDataStoreFactoryImpl(private val matchesCache: NewMatchesCache,
                                     private val diskMatchesDataStore: DiskNewMatchesDataStore,
                                     private val cloudMatchesDataStore: CloudNewMatchesDataStore
) : NewMatchesDataStoreFactory {

    override fun create(priority: NewMatchesDataStoreFactory.Priority) =
        if (priority == NewMatchesDataStoreFactory.Priority.CLOUD || !matchesCache.isCached())
            cloudMatchesDataStore
        else
            diskMatchesDataStore
}

interface NewMatchesDataStoreFactory {

    enum class Priority {
        CLOUD,
        CACHE
    }

    fun create(priority: Priority): NewMatchesDataStore
}