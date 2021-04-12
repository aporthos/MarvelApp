package com.globant.data.datasource.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.data.MockFactory.characterDto
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class CharactersLocalDataSourceImplTest {

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dao: CharactersDao

    private val dataSource by lazy {
        CharactersLocalDataSourceImpl(dao)
    }

    @Test
    fun `validate data source update characters`() {
        `when`(dao.update(characterDto())).thenReturn(1)
        val result = dataSource.updateCharacter(characterDto())
        assertEquals(1, result)
        verify(dao).update(characterDto())
    }

    @Test
    fun `validate data source insert characters`() {
        dataSource.insertCharactersTransaction(listOf(characterDto()))
        verify(dao).insertCharactersTransaction(listOf(characterDto()))
    }

    @Test
    fun `validate data source get characters by id success`() {
        `when`(dao.getCharacterById(1)).thenReturn(characterDto())
        val result = dataSource.getCharacterById(1)
        assertNotNull(result)
        result?.let {
            assertEquals(it.id, 1011334)
            assertEquals(it.name, "3-D Man")
            assertEquals(
                it.description,
                "Formerly known as Emil Blonsky, a spy of Soviet Yugoslavian origin working for the KGB, the Abomination gained his powers after receiving a dose of gamma radiation similar to that which transformed Bruce Banner into the incredible Hulk."
            )
            assertEquals(it.modified, "2012-03-20T12:32:12-0400")
            assertEquals(
                it.imageurl,
                "http://i.annihil.us/u/prod/marvel/i/mg/9/50/4ce18691cbf04.jpg"
            )
            assertEquals(it.comicsTotal, 53)
            assertEquals(it.seriesTotal, 26)
            assertEquals(it.storiesTotal, 63)
            assertEquals(it.eventsTotal, 1)
            assertEquals(it.isFavourite, false)
        }
    }

    @Test
    fun `validate data source get characters by id failed`() {
        `when`(dao.getCharacterById(1)).thenReturn(null)
        val result = dataSource.getCharacterById(1)
        assertNull(result)
    }

    @Test
    fun `validate data source get characters favourites`() {
        dataSource.getCharactersByFavouritesIs(true)
        verify(dao).getCharactersByFavouriteIs(true)
    }

}