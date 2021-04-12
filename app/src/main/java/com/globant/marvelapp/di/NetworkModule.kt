package com.globant.marvelapp.di

import android.app.Application
import com.globant.marvelapp.BuildConfig
import com.globant.shared.interfaces.NetworkUtils
import com.globant.shared.utils.Constants.API_KEY
import com.globant.shared.utils.Constants.DATE_FORMAT
import com.globant.shared.utils.Constants.HASH
import com.globant.shared.utils.Constants.TIME_OUT
import com.globant.shared.utils.Constants.TIME_STAMP
import com.globant.shared.utils.NetworkUtilsImpl
import com.globant.shared.utils.extensions.md5
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setDateFormat(DATE_FORMAT).create()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()

        interceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        return interceptor
    }

    @Singleton
    @Provides
    fun provideHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original = chain.request()
                    val originalHttpUrl = original.url
                    val hash =
                        "${BuildConfig.TIMESTAMP}${BuildConfig.PRIVATE_KEY}${BuildConfig.API_KEY}".md5()
                    val builder = originalHttpUrl.newBuilder()
                        .addQueryParameter(API_KEY, BuildConfig.API_KEY)
                        .addQueryParameter(HASH, hash)
                        .addQueryParameter(TIME_STAMP, BuildConfig.TIMESTAMP.toString())
                        .build()
                    val requestBuilder = original.newBuilder()
                        .url(builder)
                    val request = requestBuilder.build()
                    return chain.proceed(request)
                }

            })
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideNetworkStatus(app: Application): NetworkUtils = NetworkUtilsImpl(app)
}