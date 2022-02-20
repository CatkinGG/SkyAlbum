package com.my.skyalbum.model.api.vo

import com.google.gson.annotations.SerializedName

data class CompanyItem(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("catchPhrase")
    val catchPhrase: String? = null,

    @SerializedName("bs")
    val bs: String? = null
)

