package biz.ddroid.domain.interactor

interface StatisticsInteractor {
    suspend fun fetch(reload: Boolean): Status
}

