package com.aomine.directredapi.data.model.api

import com.google.gson.annotations.SerializedName

enum class ApiStatus{

    @SerializedName("success")
    OK,

    @SerializedName("error")
    BAD_REQUEST,

    @SerializedName("error")
    ERROR,

    UNKNOWN;

    companion object {
        fun from(code: Int):ApiStatus{
            when(code){
                200 -> return OK
                404 -> return ERROR
                500 -> return BAD_REQUEST
            }
            return UNKNOWN
        }
    }
}