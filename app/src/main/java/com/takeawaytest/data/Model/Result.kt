package com.takeawaytest.data.Model

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("full_name"    ) var fullName    : String? = null,
    @SerializedName("phone_number" ) var phoneNumber : String? = null,
    @SerializedName("email"        ) var email       : String? = null,
    @SerializedName("image"        ) var image       : String? = null
)
