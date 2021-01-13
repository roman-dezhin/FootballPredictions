package biz.ddroid.domain.interactor

import biz.ddroid.domain.data.NewMatchData

interface NewMatchesInteractor {

    suspend fun getNewMatches(reload: Boolean): Pair<Status, List<NewMatchData>>
}