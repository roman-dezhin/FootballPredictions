package biz.ddroid.footballpredictions.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import biz.ddroid.data.net.ConnectionManager
import biz.ddroid.data.net.ConnectionManagerImpl
import biz.ddroid.footballpredictions.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.util.concurrent.TimeUnit

object NetworkModule {
    private const val BASE_URL = "https://ddd.droman.stark.vps-private.net/predicts-api-json/"

    lateinit var connectionManager: ConnectionManager
    private lateinit var retrofit: Retrofit

    fun initialize(app: Application) {
        connectionManager = ConnectionManagerImpl(getConnectivityManager(app))
        retrofit = getRetrofit(getOkHttpClient(getInterceptor()))
    }

    fun <T> getService(className: Class<T>): T = retrofit.create(className)

    private fun getConnectivityManager(context: Context) =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    private fun getRetrofit(client: OkHttpClient) =
        Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    private fun getOkHttpClient(interceptor: Interceptor) =
        OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .cookieJar(JavaNetCookieJar(CookieManager()))
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .build()


    private fun getInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }
}