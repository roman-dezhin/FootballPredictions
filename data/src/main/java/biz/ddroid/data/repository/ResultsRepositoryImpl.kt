package biz.ddroid.data.repository

import biz.ddroid.data.entity.mapper.ResultDataMapper
import biz.ddroid.data.repository.datasource.ResultsDataStoreFactory
import biz.ddroid.domain.data.ResultData
import biz.ddroid.domain.repository.ResultsRepository

class ResultsRepositoryImpl(
    private val dataStoreFactory: ResultsDataStoreFactory,
    private val dataMapper: ResultDataMapper
) : ResultsRepository {

    override suspend fun getResults(reload: Boolean): List<ResultData> {
        val priority = if (reload) ResultsDataStoreFactory.Priority.CLOUD else ResultsDataStoreFactory.Priority.CACHE
        val dataStore = dataStoreFactory.create(priority)
        return dataMapper.map(dataStore.getEntityList())
    }
}