package com.my.skyalbum.model.api.vo

import com.google.gson.annotations.SerializedName

data class GeoItem(
    @SerializedName("lat")
    val street: String? = null,

    @SerializedName("lng")
    val suite: String? = null
)

