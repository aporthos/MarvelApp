package com.globant.data.mapper

import com.globant.data.MockFactory.characterResponse
import com.globant.data.MockFactory.characterResponseFailed
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class CharactersMapperTest {
    private val mapper by lazy {
        CharactersMapper(
            UrlsListMapper(UrlsMapper())
        )
    }

    @Test
    fun `verify transform`() {
        val response = characterResponse()
        with(mapper.mapFrom(response)) {
            this?.let {
                assertEquals(id, response.id)
                assertEquals(name, response.name)
                assertEquals(description, response.description)
                assertEquals(modified, response.modified)
                assertEquals(imageurl, response.thumbnail?.path + "/portrait_incredible.jpg")
                assertEquals(comicsTotal, response.comics?.available)
                assertEquals(seriesTotal, response.series?.available)
                assertEquals(storiesTotal, response.stories?.available)
                assertEquals(eventsTotal, response.events?.available)
                assertEquals(urls.first().type, response.urls?.first()?.type)
                assertEquals(urls.first().url, response.urls?.first()?.url)
            }
        }
    }

    @Test
    fun `verify transform failed`() {
        val response = characterResponseFailed()
        val transform = mapper.mapFrom(response)
        assertNull(transform)
    }
}