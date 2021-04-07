package biz.ddroid.data.repository

import biz.ddroid.data.entity.mapper.PredictionDataMapper
import biz.ddroid.data.repository.datasource.PredictionsDataStoreFactory
import biz.ddroid.domain.data.PredictionData
import biz.ddroid.domain.repository.PredictionsRepository

class PredictionsRepositoryImpl(
    private val dataStoreFactory: PredictionsDataStoreFactory,
    private val dataMapper: PredictionDataMapper
) : PredictionsRepository {

    override suspend fun getPredictions(reload: Boolean): List<PredictionData> {
        val priority = if (reload) PredictionsDataStoreFactory.Priority.CLOUD else PredictionsDataStoreFactory.Priority.CACHE
        val dataStore = dataStoreFactory.create(priority)
        return dataMapper.map(dataStore.getPredictionsEntityList())
    }
}