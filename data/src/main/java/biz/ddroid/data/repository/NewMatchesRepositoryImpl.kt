package biz.ddroid.data.repository

import biz.ddroid.data.entity.mapper.NewMatchDataMapper
import biz.ddroid.data.repository.datasource.NewMatchesDataStoreFactory
import biz.ddroid.domain.data.NewMatchData
import biz.ddroid.domain.repository.NewMatchesRepository

class NewMatchesRepositoryImpl(
    private val dataStoreFactory: NewMatchesDataStoreFactory,
    private val dataMapper: NewMatchDataMapper
) : NewMatchesRepository {

    override suspend fun getMatches(reload: Boolean): List<NewMatchData> {
        val priority = if (reload) NewMatchesDataStoreFactory.Priority.CLOUD else NewMatchesDataStoreFactory.Priority.CACHE
        val dataStore = dataStoreFactory.create(priority)
        return dataMapper.map(dataStore.getNewMatchesEntityList())
    }
}