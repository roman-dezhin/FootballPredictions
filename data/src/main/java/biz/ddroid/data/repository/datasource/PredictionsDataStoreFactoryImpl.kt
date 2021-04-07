package biz.ddroid.data.repository.datasource

import biz.ddroid.data.cache.PredictionsCache

class PredictionsDataStoreFactoryImpl(private val cache: PredictionsCache,
                                      private val diskDataStore: PredictionsDiskDataStore,
                                      private val cloudDataStore: PredictionsCloudDataStore
) : PredictionsDataStoreFactory {

    override fun create(priority: PredictionsDataStoreFactory.Priority) =
        if (priority == PredictionsDataStoreFactory.Priority.CLOUD || !cache.isCached())
            cloudDataStore
        else
            diskDataStore
}

interface PredictionsDataStoreFactory {

    enum class Priority {
        CLOUD,
        CACHE
    }

    fun create(priority: Priority): PredictionsDataStore
}