package biz.ddroid.domain.interactor

interface ResultsInteractor {
    suspend fun fetch(reload: Boolean): Status
}

