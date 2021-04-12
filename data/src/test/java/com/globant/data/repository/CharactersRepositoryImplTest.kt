package com.globant.data.repository

import com.globant.data.MockFactory.characterDto
import com.globant.domain.datasource.CharactersLocalDataSource
import com.globant.domain.datasource.CharactersRemoteDataSource
import com.globant.shared.data.exceptions.SelectCharacterException
import com.globant.shared.data.exceptions.UpdateException
import com.globant.shared.domain.di.AppExecutors
import com.globant.shared.interfaces.NetworkUtils
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class CharactersRepositoryImplTest {

    @Mock
    private lateinit var charactersRemoteDataSource: CharactersRemoteDataSource

    @Mock
    private lateinit var charactersLocalDataSource: CharactersLocalDataSource

    @Mock
    private lateinit var networkUtils: NetworkUtils

    @Mock
    private lateinit var executors: AppExecutors

    private val repository by lazy {
        CharactersRepositoryImpl(
            charactersRemoteDataSource,
            charactersLocalDataSource,
            networkUtils,
            executors
        )
    }

    @Test
    fun `Valication update character`() {
        `when`(charactersLocalDataSource.updateCharacter(characterDto())).thenReturn(1)
        repository.updateCharacter(characterDto())
        verify(charactersLocalDataSource).updateCharacter(characterDto())
    }

    @Test(expected= UpdateException::class)
    fun `Valication update character failed`() {
        `when`(charactersLocalDataSource.updateCharacter(characterDto())).thenReturn(0)
        val result = repository.updateCharacter(characterDto())
        assertEquals(0, result)
        verify(charactersLocalDataSource).updateCharacter(characterDto())
    }

    @Test
    fun `Valication get characters by id`() {
        `when`(charactersLocalDataSource.getCharacterById(1)).thenReturn(characterDto())
        val result = repository.getCharactersById(1)
        assertNotNull(result)
        verify(charactersLocalDataSource).getCharacterById(1)
    }

    @Test(expected= SelectCharacterException::class)
    fun `Valication get characters by id failed`() {
        `when`(charactersLocalDataSource.getCharacterById(1)).thenReturn(null)
        val result = repository.getCharactersById(1)
        assertNull(result)
        verify(charactersLocalDataSource).getCharacterById(1)
    }

}