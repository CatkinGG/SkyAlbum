package com.my.skyalbum.model.api.vo

import com.google.gson.annotations.SerializedName

data class AddressItem(
    @SerializedName("street")
    val street: String? = null,

    @SerializedName("suite")
    val suite: String? = null,

    @SerializedName("city")
    val city: String? = null,

    @SerializedName("zipcode")
    val zipcode: String? = null,

    @SerializedName("geo")
    val geo: GeoItem? = null,
)

