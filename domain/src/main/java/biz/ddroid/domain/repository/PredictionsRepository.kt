package biz.ddroid.domain.repository

import biz.ddroid.domain.data.PredictionData

interface PredictionsRepository {
    suspend fun getPredictions(reload: Boolean = false): List<PredictionData>
}