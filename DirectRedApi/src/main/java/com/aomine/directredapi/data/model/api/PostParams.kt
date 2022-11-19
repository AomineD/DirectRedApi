package com.aomine.directredapi.data.model.api

import com.aomine.directredapi.data.model.SportEvent
import com.google.gson.annotations.SerializedName
data class
PostParams(
    @SerializedName("event_name") val event_name:String?
)

fun SportEvent.toPost():PostParams =
    PostParams(matchName)

