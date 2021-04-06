package biz.ddroid.data.net

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

object ServiceInterceptor : Interceptor {
    const val NO_AUTH_HEADER_KEY = "No-Authentication"

    private var xCSRFToken: String = ""

    fun setToken(token: String) {
        xCSRFToken = token
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val requestBuilder = request.newBuilder()

        if (request.header(NO_AUTH_HEADER_KEY) == null) {
            if (xCSRFToken == "") {
                throw Exception("X-CSRF token should be defined for auth apis")
            }

            requestBuilder.addHeader("X-CSRF-Token", xCSRFToken)
        }

        return chain.proceed(requestBuilder.build())
    }

}