package com.testdeymervilla.network.di

import com.testdeymervilla.network.BuildConfig
import com.testdeymervilla.network.api.ApiService
import com.testdeymervilla.network.constants.NetworkConstants.Defaults.DEFAULT_TIMEOUT
import com.testdeymervilla.network.interceptors.LoginHeadersInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideLoginHeadersInterceptor() =
        LoginHeadersInterceptor()

    @Singleton
    @Provides
    fun provideOkHttpClientApi(
        loginHeadersInterceptor: LoginHeadersInterceptor
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .callTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(loginHeadersInterceptor)
            .apply { if(BuildConfig.DEBUG) addInterceptor(logging) }
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitApi(
        client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService = retrofit.create(
        ApiService::class.java
    )
}