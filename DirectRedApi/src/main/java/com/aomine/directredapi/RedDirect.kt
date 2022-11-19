package com.aomine.directredapi

import com.aomine.directredapi.data.converter.EventConverter.removeInvalids
import com.aomine.directredapi.data.model.*
import com.aomine.directredapi.data.model.api.ApiStatus
import com.aomine.directredapi.data.model.api.EventApiResponse
import com.aomine.directredapi.data.model.api.EventsResponse
import com.aomine.directredapi.data.model.api.PlayersResponse
import com.aomine.directredapi.data.service.RedService
import com.aomine.directredapi.helper.RedCoroutinesHelper.ioSafe
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject


class RedDirect @Inject constructor(private val redService: RedService) {


    init {
        loadPlayersFromApi {
           players = it.players
        }
    }

    var players:List<Player>? = null

    companion object{
        var baseUrl:String = "https://www.tarjetarojatvonline.sx"
        lateinit var instance:RedDirect

    }

    fun loadSportEvents(response: suspend (CoroutineScope.(SportListResponse) -> Unit)){
        ioSafe {
            val scrappingResponse = redService.getEventsList()
            val dbResponse = redService.getEvents()
            response(scrappingResponse.removeInvalids(dbResponse))
        }
    }

    fun loadEventFromApi(sportEvent: SportEvent, response: suspend (CoroutineScope.(EventApiResponse) -> Unit)){

        ioSafe {
            if(sportEvent.eventUrl == null){
                response(EventApiResponse(ApiStatus.BAD_REQUEST, "Event url is null", null))
                return@ioSafe
            }
            response(redService.getEventUrl(sportEvent))
        }
    }

   private fun loadEventsFromApi(response: suspend (CoroutineScope.(EventsResponse) -> Unit)){
        ioSafe {
            response(redService.getEvents())
        }
    }

    fun loadPlayersFromApi(response: suspend (CoroutineScope.(PlayersResponse) -> Unit)){
        ioSafe {
            response(redService.getPlayers())
        }
    }
}
