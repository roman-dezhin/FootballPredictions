package biz.ddroid.domain.interactor

import biz.ddroid.domain.data.PredictionData

interface PredictionsInteractor {

    suspend fun fetch(reload: Boolean): Result<List<PredictionData>>
}

