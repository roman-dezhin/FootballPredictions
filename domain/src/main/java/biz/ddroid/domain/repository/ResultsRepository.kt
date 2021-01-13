package biz.ddroid.domain.repository

import biz.ddroid.domain.data.ResultData

interface ResultsRepository {
    suspend fun getResults(reload: Boolean = false): List<ResultData>
}