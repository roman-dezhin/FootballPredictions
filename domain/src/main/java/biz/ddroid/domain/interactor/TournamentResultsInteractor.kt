package biz.ddroid.domain.interactor

interface TournamentResultsInteractor {
    suspend fun fetch(reload: Boolean): Status
}

