package com.globant.data.models.remote.response

import com.google.gson.annotations.SerializedName

data class UrlsEntity(
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("url")
    val url: String? = null
)