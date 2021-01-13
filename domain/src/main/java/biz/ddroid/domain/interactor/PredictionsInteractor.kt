package biz.ddroid.domain.interactor

interface PredictionsInteractor {
    suspend fun fetch(reload: Boolean): Status
}

