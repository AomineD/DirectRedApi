package com.aomine.directredapi.data.model

data class
SportListResponse(val statusCode: Int,
                  val errorMessage:String,
                  val list: List<SportEvent>?)
