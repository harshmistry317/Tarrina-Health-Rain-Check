package com.hkm.tarrina_health_rain_check.di

import android.app.Application
import com.hkm.tarrina_health_rain_check.BuildConfig
import com.hkm.tarrina_health_rain_check.data.datasource.RainCheckDataSource
import com.hkm.tarrina_health_rain_check.data.network.RainCheckApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    class HeaderInterceptor @Inject constructor() : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()

//            val token = tokenProvider()
            val newRequest = original.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
//                .apply {
//                    if (!token.isNullOrEmpty()) {
//                        addHeader("Authorization", "Bearer $token")
//                    }
//                }  if you have token then un-comment it.
                .build()

            return chain.proceed(newRequest)
        }
    }


    @Provides
    fun provideHeaderInterceptor(): Interceptor = HeaderInterceptor()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG){
                level = HttpLoggingInterceptor.Level.BODY
            }

        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor,
                            headerInterceptor: HeaderInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()


    @Provides
    @Singleton
    fun provideRainCheckApi(retrofit: Retrofit) : RainCheckApi = retrofit.create(RainCheckApi::class.java)

    @Provides
    @Singleton
    fun provideRainCheckDataSource(
        application: Application,
        rainCheckApi: RainCheckApi
    ): RainCheckDataSource = RainCheckDataSource(application,
        rainCheckApi
    )

}