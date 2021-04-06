package biz.ddroid.data.net

import biz.ddroid.data.entity.NewMatchesEntity
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface MatchesService {
    @POST("predict/new")
    fun getNewMatchesAsync() : Deferred<Response<List<NewMatchesEntity>>>
}