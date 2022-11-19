package com.aomine.directredapi.data.service

import com.aomine.directredapi.RedDirect
import com.aomine.directredapi.data.converter.EventConverter.convertInEvents
import com.aomine.directredapi.data.model.*
import com.aomine.directredapi.data.model.api.*
import com.lagradost.nicehttp.Requests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RedService @Inject constructor(private val redApiClient: RedApiClient, private val requests: Requests) {

    suspend fun getEventsList():SportListResponse {
        return withContext(Dispatchers.IO){
            val niceResponse = requests.get(RedDirect.baseUrl)

            val httpResponse = niceResponse.okhttpResponse
            val response = SportListResponse(
                httpResponse.code,
                httpResponse.message, niceResponse.document.convertInEvents())
            response
        }
    }

    suspend fun getEventUrl(sportEvent: SportEvent):EventApiResponse{
        return withContext(Dispatchers.IO){
            val response = redApiClient.getEvent(sportEvent.matchName).execute()
           response.body() ?: EventApiResponse(status = ApiStatus.from(response.code()),
            message = response.message(),
            eventUrls = null)
        }
    }

    suspend fun getEvents():EventsResponse{
        return withContext(Dispatchers.IO){
            val response = redApiClient.getEventsFromApi().execute()
            response.body() ?: EventsResponse(status = ApiStatus.from(response.code()),
                message = response.message(),
                events = null)
        }
    }

    suspend fun getPlayers():PlayersResponse{
        return withContext(Dispatchers.IO){
            val response = redApiClient.getPlayers().execute()
            response.body() ?: PlayersResponse(status = ApiStatus.from(response.code()),
                message = response.message(),
                players = null)
        }
    }
}