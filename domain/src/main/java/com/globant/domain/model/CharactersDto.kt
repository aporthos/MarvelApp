package com.globant.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharactersDto(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val modified: String,
    val imageurl: String,
    val comicsTotal: Int,
    val seriesTotal: Int,
    val storiesTotal: Int,
    val eventsTotal: Int,
    val urls: List<UrlsDto>,
    var isFavourite: Boolean = false
)