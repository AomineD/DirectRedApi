package com.aomine.directredapi.data.service

import com.aomine.directredapi.data.model.api.EventApiResponse
import com.aomine.directredapi.data.model.api.EventsResponse
import com.aomine.directredapi.data.model.api.PlayersResponse
import org.jsoup.nodes.Document
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

const val UserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36 football/2023"

interface RedApiClient {
    @Headers("User-Agent: $UserAgent")
    @GET("/")
    suspend fun getEventsFromScrap():Response<Document>

    @Headers("User-Agent: $UserAgent")
    @POST("getEventInfo")
    @FormUrlEncoded
    fun getEvent(@Field("event_name") event_name:String?):Call<EventApiResponse>

    @Headers("User-Agent: $UserAgent")
    @POST("getEvents")
    fun getEventsFromApi():Call<EventsResponse>

    @Headers("User-Agent: $UserAgent")
    @POST("getPlayers")
    fun getPlayers():Call<PlayersResponse>
}