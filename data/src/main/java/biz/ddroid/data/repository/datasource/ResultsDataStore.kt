package biz.ddroid.data.repository.datasource

import biz.ddroid.data.entity.ResultEntity

interface ResultsDataStore {

    suspend fun getEntityList(): List<ResultEntity>
}