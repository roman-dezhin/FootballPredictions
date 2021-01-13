package biz.ddroid.domain.repository

import biz.ddroid.domain.data.NewMatchData

interface NewMatchesRepository {
    suspend fun getMatches(reload: Boolean = false): List<NewMatchData>
}