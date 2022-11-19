package com.aomine.directredapi.data.model.api

import com.google.gson.annotations.SerializedName

data class EventsResponse(
    @SerializedName("status") val status:ApiStatus? = null,
    @SerializedName("message") val message:String? = null,
    @SerializedName("events") val events:List<String>?
)
