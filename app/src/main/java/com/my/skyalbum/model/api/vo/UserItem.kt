package com.my.skyalbum.model.api.vo

import com.google.gson.annotations.SerializedName

data class UserItem(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("username")
    val username: String? = null,

    @SerializedName("address")
    val address: AddressItem? = null,

    @SerializedName("phone")
    val phone: String? = null,

    @SerializedName("website")
    val website: String? = null,

    @SerializedName("company")
    val company: CompanyItem? = null,
)

