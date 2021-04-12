package com.globant.data.models.remote.response

import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("modified")
    val modified: String? = null,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailEntity? = null,
    @SerializedName("comics")
    val comics: ComicsEntity? = null,
    @SerializedName("series")
    val series: SeriesEntity? = null,
    @SerializedName("stories")
    val stories: StoriesEntity? = null,
    @SerializedName("events")
    val events: EventsEntity? = null,
    @SerializedName("urls")
    val urls: List<UrlsEntity>? = listOf()
)