package com.takeawaytest.data.Model

import com.google.gson.annotations.SerializedName

data class ContactListResponce(
    @SerializedName("error"  ) var error  : Error?            = Error(),
    @SerializedName("result" ) var result : ArrayList<Result> = arrayListOf(),
    @SerializedName("status" ) var status : Boolean?          = null
)
