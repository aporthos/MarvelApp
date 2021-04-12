package com.globant.data.mapper

import com.globant.data.MockFactory.response
import com.globant.data.MockFactory.responseFailed
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class CharactersListMapperTest {
    private val mapper by lazy {
        CharactersListMapper(CharactersMapper(UrlsListMapper(UrlsMapper())))
    }

    @Test
    fun `verify transform`() {
        with(mapper.mapFrom(response)) {
            assertEquals(first().id, response.data?.results?.first()?.id)
            assertEquals(first().name, response.data?.results?.first()?.name)
            assertEquals(first().description, response.data?.results?.first()?.description)
            assertEquals(first().modified, response.data?.results?.first()?.modified)
            assertEquals(
                first().imageurl,
                response.data?.results?.first()?.thumbnail?.path + "/portrait_incredible.jpg"
            )
            assertEquals(first().comicsTotal, response.data?.results?.first()?.comics?.available)
            assertEquals(first().seriesTotal, response.data?.results?.first()?.series?.available)
            assertEquals(first().storiesTotal, response.data?.results?.first()?.stories?.available)
            assertEquals(first().eventsTotal, response.data?.results?.first()?.events?.available)
            assertEquals(first().urls.first().url, response.data?.results?.first()?.urls?.first()?.url)
            assertEquals(first().urls.first().type, response.data?.results?.first()?.urls?.first()?.type)
        }
    }

    @Test
    fun `verify transform failed`() {
        val response = mapper.mapFrom(responseFailed)
        assertEquals(response.size, 0)
    }
}