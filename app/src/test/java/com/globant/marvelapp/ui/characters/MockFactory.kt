package com.globant.marvelapp.ui.characters

import com.globant.domain.model.CharactersDto
import com.globant.domain.model.UrlsDto

object MockFactory {
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
        urls = listUrl(),
        isFavourite = false
    )

    fun listUrl() = listOf(
        UrlsDto(
            type = "detail",
            url = "http://marvel.com/characters/76/a-bomb?utm_campaign=apiRef&utm_source=eb8202d08b009675a700b4bfd597d066"
        ),
        UrlsDto(
            type = "wiki",
            url = "http://marvel.com/universe/A.I.M.?utm_campaign=apiRef&utm_source=eb8202d08b009675a700b4bfd597d066"
        ),
        UrlsDto(
            type = "comiclink",
            url = "http://marvel.com/comics/characters/1017100/a-bomb_has?utm_campaign=apiRef&utm_source=eb8202d08b009675a700b4bfd597d066"
        )
    )
}