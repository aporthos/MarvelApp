package com.globant.data

import com.globant.data.models.remote.response.*
import com.globant.domain.model.CharactersDto
import com.globant.shared.data.network.ApiCommon
import com.globant.shared.data.network.ApiCommonData

object MockFactory {

    val response = ApiCommon(
        code = 200,
        status = "Ok",
        copyright = "© 2021 MARVEL",
        attributionText = "Data provided by Marvel. © 2021 MARVEL",
        attributionHTML = "<a href=\\\"http://marvel.com\\\">Data provided by Marvel. © 2021 MARVEL</a>",
        etag = "a1250f4f65813ca0708ff99f184343695f216d4a",
        data = ApiCommonData(
            offset = 0,
            limit = 50,
            total = 1493,
            count = 50,
            results = listOf(
                characterResponse(),
                characterResponse()
            )
        )
    )

    val responseFailed = ApiCommon(
        code = 200,
        status = "Ok",
        copyright = "© 2021 MARVEL",
        attributionText = "Data provided by Marvel. © 2021 MARVEL",
        attributionHTML = "<a href=\\\"http://marvel.com\\\">Data provided by Marvel. © 2021 MARVEL</a>",
        etag = "a1250f4f65813ca0708ff99f184343695f216d4a",
        data = ApiCommonData(
            offset = 0,
            limit = 50,
            total = 1493,
            count = 50,
            results = listOf<CharactersResponse>()
        )
    )

    fun characterResponse() = CharactersResponse(
        id = 1011334,
        name = "3-D Man",
        description = "Formerly known as Emil Blonsky, a spy of Soviet Yugoslavian origin working for the KGB, the Abomination gained his powers after receiving a dose of gamma radiation similar to that which transformed Bruce Banner into the incredible Hulk.",
        modified = "2012-03-20T12:32:12-0400",
        thumbnail = ThumbnailEntity(
            path = "http://i.annihil.us/u/prod/marvel/i/mg/9/50/4ce18691cbf04",
            extension = "jpg"
        ),
        comics = ComicsEntity(
            available = 53,
            collectionURI = "http://gateway.marvel.com/v1/public/characters/1009146/comics"
        ),
        series = SeriesEntity(
            available = 26,
            collectionURI = "http://gateway.marvel.com/v1/public/characters/1009146/series"
        ),
        stories = StoriesEntity(
            available = 63,
            collectionURI = "http://gateway.marvel.com/v1/public/characters/1009146/stories"
        ),
        events = EventsEntity(
            available = 1,
            collectionURI = "http://gateway.marvel.com/v1/public/characters/1009146/events"
        ),
        urls = listOf(urlEntity())
        )

    fun characterResponseFailed() = CharactersResponse(
        id = null,
        name = "3-D Man",
        description = "Formerly known as Emil Blonsky, a spy of Soviet Yugoslavian origin working for the KGB, the Abomination gained his powers after receiving a dose of gamma radiation similar to that which transformed Bruce Banner into the incredible Hulk.",
        modified = "2012-03-20T12:32:12-0400",
        thumbnail = ThumbnailEntity(
            path = "http://i.annihil.us/u/prod/marvel/i/mg/9/50/4ce18691cbf04",
            extension = "jpg"
        ),
        comics = ComicsEntity(
            available = 53,
            collectionURI = "http://gateway.marvel.com/v1/public/characters/1009146/comics"
        ),
        series = SeriesEntity(
            available = 26,
            collectionURI = "http://gateway.marvel.com/v1/public/characters/1009146/series"
        ),
        stories = StoriesEntity(
            available = 63,
            collectionURI = "http://gateway.marvel.com/v1/public/characters/1009146/stories"
        ),
        events = EventsEntity(
            available = 1,
            collectionURI = "http://gateway.marvel.com/v1/public/characters/1009146/events"
        ),
        urls = listOf(urlEntity())
    )

    fun urlEntity() = UrlsEntity(
        type = "detail",
        url = "http://marvel.com/characters/81/abomination?utm_campaign=apiRef&utm_source=eb8202d08b009675a700b4bfd597d066"
    )

    fun urlEntityFailed() = UrlsEntity(
        type = null,
        url = null
    )

    fun characterDto() = CharactersDto(
        id = 1011334,
        name = "3-D Man",
        description = "Formerly known as Emil Blonsky, a spy of Soviet Yugoslavian origin working for the KGB, the Abomination gained his powers after receiving a dose of gamma radiation similar to that which transformed Bruce Banner into the incredible Hulk.",
        modified = "2012-03-20T12:32:12-0400",
        imageurl = "http://i.annihil.us/u/prod/marvel/i/mg/9/50/4ce18691cbf04.jpg",
        comicsTotal = 53,
        seriesTotal = 26,
        storiesTotal = 63,
        eventsTotal = 1,
        urls = listOf(),
        isFavourite = false
    )

}