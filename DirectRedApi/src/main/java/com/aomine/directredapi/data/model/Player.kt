package com.aomine.directredapi.data.model

import com.aomine.directredapi.data.model.api.ApiStatus
import com.google.gson.annotations.SerializedName

data class Player(@SerializedName("player_name") val playerName:String?,
                  @SerializedName("player_url") val playerUrl:String?)
