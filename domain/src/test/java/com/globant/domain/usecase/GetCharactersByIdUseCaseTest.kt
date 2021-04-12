package com.globant.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.domain.MockFactory.characterDto
import com.globant.domain.repository.CharactersRepository
import com.globant.shared.data.network.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.StrictStubs::class)
class GetCharactersByIdUseCaseTest {

    @Mock
    private lateinit var repository: CharactersRepository

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    private val useCase by lazy { GetCharactersByIdUseCase(repository, TestCoroutineDispatcher()) }

    @Test
    fun `validation use case success`() = runBlockingTest {
        `when`(repository.getCharactersById(1)).thenReturn(characterDto())
        val useCase = useCase(1)
        assertTrue(useCase is State.Success)
        val result = useCase as State.Success
        result.data.let {
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
    fun `validation use case failed`() = runBlockingTest {
        `when`(repository.getCharactersById(1)).thenThrow()
        val useCase = useCase(1)
        assertTrue(useCase is State.Error)
    }
}