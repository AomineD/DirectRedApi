package com.aomine.directredapi.di

import android.content.Context
import com.aomine.directredapi.R
import com.aomine.directredapi.data.service.RedApiClient
import com.aomine.directredapi.data.service.UserAgent
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.lagradost.nicehttp.Requests
import com.lagradost.nicehttp.ResponseParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton
import kotlin.reflect.KClass


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(@ApplicationContext appContext: Context): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(appContext.getString(R.string.api_url_red))
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiClient(retrofit: Retrofit):RedApiClient{
        return retrofit.create(RedApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideHttpNice():Requests{
       return Requests(responseParser = object : ResponseParser {
            val mapper: ObjectMapper = jacksonObjectMapper().configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false
            )

            override fun <T : Any> parse(text: String, kClass: KClass<T>): T {
                return mapper.readValue(text, kClass.java)
            }

            override fun <T : Any> parseSafe(text: String, kClass: KClass<T>): T? {
                return try {
                    mapper.readValue(text, kClass.java)
                } catch (e: Exception) {
                    null
                }
            }

            override fun writeValueAsString(obj: Any): String {
                return mapper.writeValueAsString(obj)
            }
        }).apply {
            defaultHeaders = mapOf("user-agent" to UserAgent)
        }
    }
}