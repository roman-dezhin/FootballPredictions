package biz.ddroid.data.repository.datasource

import biz.ddroid.data.entity.PredictionEntity

interface PredictionsDataStore {

    suspend fun getEntityList(): List<PredictionEntity>

}