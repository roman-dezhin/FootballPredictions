package biz.ddroid.data.repository.datasource

import biz.ddroid.data.entity.NewMatchesEntity

interface NewMatchesDataStore {

    suspend fun getNewMatchesEntityList(): List<NewMatchesEntity>

}