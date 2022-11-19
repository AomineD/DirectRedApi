package com.aomine.directredapi.data.model

import com.aomine.directredapi.data.model.api.MapParams
import com.google.gson.annotations.SerializedName

data class
EventUrl(
    @SerializedName("player_id") val playerId:String?,
    @SerializedName("postParams") val postParams: List<MapParams>?
)
