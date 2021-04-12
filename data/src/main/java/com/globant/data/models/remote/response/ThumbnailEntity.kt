package com.globant.data.models.remote.response

import com.google.gson.annotations.SerializedName

data class ThumbnailEntity(
    @SerializedName("path")
    val path: String? = null,
    @SerializedName("extension")
    val extension: String? = null
)