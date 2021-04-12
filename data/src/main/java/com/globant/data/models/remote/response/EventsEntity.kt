package com.globant.data.models.remote.response

import com.google.gson.annotations.SerializedName

data class EventsEntity(
    @SerializedName("available")
    val available: Int? = 0,
    @SerializedName("collectionURI")
    val collectionURI: String? = null
)