package com.globant.data.mapper

import com.globant.data.models.remote.response.CharactersResponse
import com.globant.domain.model.CharactersDto
import com.globant.shared.data.mapper.Mapper
import com.globant.shared.utils.extensions.value
import javax.inject.Inject

class CharactersMapper @Inject constructor(val mapper: UrlsListMapper) :
    Mapper<CharactersResponse, CharactersDto?>() {
    companion object {
        private const val EXTENSION = ".jpg"
        private const val IMAGE_VARIANT_NAME = "portrait_incredible"
    }

    override fun mapFrom(from: CharactersResponse): CharactersDto? {
        return from.id?.let {
            CharactersDto(
                id = from.id,
                name = from.name.value(),
                description = from.description.value(),
                modified = from.modified.value(),
                imageurl = createImageUrl(from.thumbnail?.path),
                comicsTotal = from.comics?.available.value(),
                seriesTotal = from.series?.available.value(),
                storiesTotal = from.stories?.available.value(),
                eventsTotal = from.events?.available.value(),
                urls = mapper.mapFrom(from = from.urls)
            )
        }
    }

    private fun createImageUrl(url: String?): String = "$url/$IMAGE_VARIANT_NAME$EXTENSION"
}