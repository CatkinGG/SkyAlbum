package com.my.skyalbum.model.api.vo

import com.google.gson.annotations.SerializedName

data class AlbumItem(
    @SerializedName("userId")
    val userId: Long? = null,

    @SerializedName("id")
    val id: Long,

    @SerializedName("title")
    val title: String? = null,

    val photoUrl: String? = null
)

