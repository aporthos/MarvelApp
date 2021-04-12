package com.globant.data.mapper

import com.globant.data.MockFactory.urlEntity
import com.globant.data.models.remote.response.UrlsEntity
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class UrlsListMapperTest {

    private val mapper by lazy { UrlsListMapper(UrlsMapper()) }

    @Test
    fun `verify transform`() {
        val response = listOf(urlEntity())
        with(mapper.mapFrom(response)) {
            assertEquals(first().type, response.first().type)
            assertEquals(first().url, response.first().url)
        }
    }

    @Test
    fun `verify transform failed`() {
        val response: List<UrlsEntity>? = null
        val transform = mapper.mapFrom(response)
        assertEquals(transform.size, 0)
    }
}