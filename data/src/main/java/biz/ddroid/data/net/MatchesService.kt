package biz.ddroid.data.net

import biz.ddroid.data.entity.NewMatchesEntity
import biz.ddroid.data.entity.PredictionEntity
import biz.ddroid.data.entity.ResultEntity
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface MatchesService {
    @POST("predict/new")
    fun getNewMatchesAsync() : Deferred<Response<List<NewMatchesEntity>>>

    @POST("predict/pending")
    fun getPredictionsAsync() : Deferred<Response<List<PredictionEntity>>>

    @POST("predict/completed")
    fun getResultsAsync() : Deferred<Response<List<ResultEntity>>>
}