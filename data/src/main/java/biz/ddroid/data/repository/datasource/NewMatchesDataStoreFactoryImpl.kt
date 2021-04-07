package biz.ddroid.data.repository.datasource

import biz.ddroid.data.cache.NewMatchesCache

class NewMatchesDataStoreFactoryImpl(private val cache: NewMatchesCache,
                                     private val diskDataStore: NewMatchesDiskDataStore,
                                     private val cloudDataStore: NewMatchesCloudDataStore
) : NewMatchesDataStoreFactory {

    override fun create(priority: NewMatchesDataStoreFactory.Priority) =
        if (priority == NewMatchesDataStoreFactory.Priority.CLOUD || !cache.isCached())
            cloudDataStore
        else
            diskDataStore
}

interface NewMatchesDataStoreFactory {

    enum class Priority {
        CLOUD,
        CACHE
    }

    fun create(priority: Priority): NewMatchesDataStore
}