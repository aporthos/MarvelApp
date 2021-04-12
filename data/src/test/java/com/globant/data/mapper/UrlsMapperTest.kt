package com.globant.data.mapper

import com.globant.data.MockFactory.urlEntity
import com.globant.data.MockFactory.urlEntityFailed
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class UrlsMapperTest {
    private val mapper by lazy { UrlsMapper() }

    @Test
    fun `verify transform`() {
        val response = urlEntity()
        with(mapper.mapFrom(response)) {
            assertEquals(type, response.type)
            assertEquals(url, response.url)
        }
    }

    @Test
    fun `verify transform failed`() {
        val response = urlEntityFailed()
        val transform = mapper.mapFrom(response)
        assertEquals(transform.type, "")
        assertEquals(transform.url, "")
    }
}