package com.aomine.directredapi.data.model.api

import com.aomine.directredapi.data.model.Player
import com.google.gson.annotations.SerializedName

data class PlayersResponse(@SerializedName("status") val status:ApiStatus? = null,
                           @SerializedName("message") val message:String? = null,
                           @SerializedName("players") val players:List<Player>?)
