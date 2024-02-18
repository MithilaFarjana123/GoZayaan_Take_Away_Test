package com.takeawaytest.data.Model

import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("code"    ) var code    : String? = null
)
