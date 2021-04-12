package com.globant.domain

import com.globant.domain.model.CharactersDto

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
        urls = listOf(),
        isFavourite = false
    )

}