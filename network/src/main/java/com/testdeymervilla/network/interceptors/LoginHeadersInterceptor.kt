package com.testdeymervilla.network.interceptors

import com.testdeymervilla.network.constants.NetworkConstants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class LoginHeadersInterceptor @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath
        if (!path.contains(NetworkConstants.Paths.Security.LOGIN)) {
            return chain.proceed(request)
        }
        val requestWithHeaders = request.newBuilder()
            .addHeader(
                NetworkConstants.Headers.USER,
                NetworkConstants.HeaderValues.USER
            ).addHeader(
                NetworkConstants.Headers.IDENTIFICATION,
                NetworkConstants.HeaderValues.IDENTIFICATION
            ).addHeader(
                NetworkConstants.Headers.USER_ID,
                NetworkConstants.HeaderValues.USER_ID
            ).addHeader(
                NetworkConstants.Headers.SERVICE_CENTER_ID,
                NetworkConstants.HeaderValues.SERVICE_CENTER_ID
            ).addHeader(
                NetworkConstants.Headers.SERVICE_CENTER_NAME,
                NetworkConstants.HeaderValues.SERVICE_CENTER_NAME
            ).addHeader(
                NetworkConstants.Headers.ORIGIN_APP_ID,
                NetworkConstants.HeaderValues.ORIGIN_APPLICATION_ID
            ).addHeader(
                NetworkConstants.Headers.ACCEPT,
                NetworkConstants.Headers.ACCEPT_JSON
            ).addHeader(
                NetworkConstants.Headers.CONTENT_TYPE,
                NetworkConstants.Headers.CONTENT_TYPE_JSON
            ).build()
        return chain.proceed(requestWithHeaders)
    }
}