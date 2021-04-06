package biz.ddroid.data.net

import biz.ddroid.data.entity.LoginRequest
import biz.ddroid.data.entity.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserService {
    @POST("user/login")
    @Headers("No-Authentication: true")
    fun loginAsync(@Body request: LoginRequest): Deferred<Response<LoginResponse>>

}