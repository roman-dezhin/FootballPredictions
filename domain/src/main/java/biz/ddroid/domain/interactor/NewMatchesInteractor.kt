package biz.ddroid.domain.interactor

import biz.ddroid.domain.data.NewMatchData

interface NewMatchesInteractor {

    suspend fun fetch(reload: Boolean): Result<List<NewMatchData>>
}