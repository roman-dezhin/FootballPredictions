package biz.ddroid.data.repository.datasource

import biz.ddroid.data.cache.ResultsCache

class ResultsDataStoreFactoryImpl(private val cache: ResultsCache,
                                  private val diskDataStore: ResultsDiskDataStore,
                                  private val cloudDataStore: ResultsCloudDataStore
) : ResultsDataStoreFactory {

    override fun create(priority: ResultsDataStoreFactory.Priority) =
        if (priority == ResultsDataStoreFactory.Priority.CLOUD || !cache.isCached())
            cloudDataStore
        else
            diskDataStore
}

interface ResultsDataStoreFactory {

    enum class Priority {
        CLOUD,
        CACHE
    }

    fun create(priority: Priority): ResultsDataStore
}