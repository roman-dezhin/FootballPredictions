package biz.ddroid.domain.repository

import biz.ddroid.domain.data.TournamentResultData

interface TournamentResultsRepository {
    suspend fun getTournamentResults(reload: Boolean = false): List<TournamentResultData>
}