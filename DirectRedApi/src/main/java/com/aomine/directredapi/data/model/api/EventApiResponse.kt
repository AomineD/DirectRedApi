package com.aomine.directredapi.data.model.api

import com.aomine.directredapi.data.model.EventUrl
import com.google.gson.annotations.SerializedName



data class EventApiResponse(
   @SerializedName("status") val status:ApiStatus? = null,
   @SerializedName("message") val message:String? = null,
   @SerializedName("event_urls") val eventUrls: List<EventUrl>?
)