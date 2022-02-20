package com.my.skyalbum.model.api.vo

import com.google.gson.annotations.SerializedName

data class PhotoItem(
    @SerializedName("albumId")
    val albumId: Long? = null,

    @SerializedName("id")
    val id: Long,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("url")
    val url: String? = null,

    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String? = null
)

