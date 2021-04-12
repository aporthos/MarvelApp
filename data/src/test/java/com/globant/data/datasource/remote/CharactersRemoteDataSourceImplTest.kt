package com.globant.data.datasource.remote

import com.globant.data.MockFactory.characterResponse
import com.globant.data.MockFactory.response
import com.globant.data.mapper.CharactersListMapper
import com.globant.data.mapper.CharactersMapper
import com.globant.data.mapper.UrlsListMapper
import com.globant.data.mapper.UrlsMapper
import com.globant.data.services.CharactersService
import com.globant.shared.data.network.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class CharactersRemoteDataSourceImplTest {

    @Mock
    private lateinit var services: CharactersService

    private val mapperOther by lazy {
        CharactersMapper(
            UrlsListMapper(UrlsMapper())
        )
    }

    private val mapper by lazy {
        CharactersListMapper(
            CharactersMapper(
                UrlsListMapper(
                    UrlsMapper()
                )
            )
        )
    }

    private val dataSource by lazy {
        CharactersRemoteDataSourceImpl(
            services,
            mapper
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Validate get characters by id success`() = runBlockingTest {
        `when`(services.getCharacterById(1)).thenReturn(Response.success(response))

        val data = dataSource.getCharactersById(1)
        assertTrue(data is State.Success)
        val result = data as State.Success
        assertEquals(result.data.size, 2)
        assertEquals(result.data.first(), mapperOther.mapFrom(characterResponse()))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Validate get characters by id error`() = runBlockingTest {
        `when`(services.getCharacterById(1)).thenReturn(Response.error(401, commonJsonError()))

        val data = dataSource.getCharactersById(1)
        assertTrue(data is State.Error)
        val result = data as State.Error
        assertEquals(result.exception, "InvalidCredentials")
    }

    private fun commonJsonError() = ResponseBody.create(
        MediaType.parse("application/json"), """
                {
                    "code": "InvalidCredentials",
                    "message": "That hash, timestamp and key combination is invalid."
                }
            """.trimIndent()
    )
}