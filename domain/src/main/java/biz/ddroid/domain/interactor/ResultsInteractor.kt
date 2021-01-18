package biz.ddroid.domain.interactor

import biz.ddroid.domain.data.ResultData

interface ResultsInteractor {

    suspend fun fetch(reload: Boolean): Result<List<ResultData>>
}

