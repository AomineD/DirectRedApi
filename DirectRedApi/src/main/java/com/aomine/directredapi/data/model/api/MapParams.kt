package com.aomine.directredapi.data.model.api

import com.google.gson.annotations.SerializedName

data class MapParams(
    @SerializedName("key_m") val key:String?,
    @SerializedName("value_m") val value:String?
)