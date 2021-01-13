package biz.ddroid.data.repository

import biz.ddroid.data.entity.mapper.NewMatchDataMapper
import biz.ddroid.data.repository.datasource.NewMatchesDataStoreFactory
import biz.ddroid.domain.data.NewMatchData
import biz.ddroid.domain.repository.NewMatchesRepository

class NewMatchesRepositoryImpl(
    private val newMatchesDataStoreFactory: NewMatchesDataStoreFactory,
    private val newMatchDataMapper: NewMatchDataMapper
) : NewMatchesRepository {

    override suspend fun getMatches(reload: Boolean): List<NewMatchData> {
        val priority = if (reload) NewMatchesDataStoreFactory.Priority.CLOUD else NewMatchesDataStoreFactory.Priority.CACHE
        val dataStore = newMatchesDataStoreFactory.create(priority)
        return newMatchDataMapper.map(dataStore.getNewMatchesEntityList())
    }
}