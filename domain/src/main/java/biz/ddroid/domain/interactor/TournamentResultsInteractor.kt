package biz.ddroid.domain.interactor

import biz.ddroid.domain.data.TournamentResultData

interface TournamentResultsInteractor {

    suspend fun fetch(reload: Boolean): Result<List<TournamentResultData>>
}

