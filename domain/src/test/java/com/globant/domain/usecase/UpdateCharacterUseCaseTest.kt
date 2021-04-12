package com.globant.domain.usecase

import com.globant.domain.MockFactory.characterDto
import com.globant.domain.repository.CharactersRepository
import com.globant.shared.data.network.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.StrictStubs::class)
class UpdateCharacterUseCaseTest {

    @Mock
    private lateinit var repository: CharactersRepository

    private val useCase by lazy {
        UpdateCharacterUseCase(repository, TestCoroutineDispatcher())
    }

    @Test
    fun `validation use case success`() = runBlockingTest {
        val useCase = useCase(characterDto())
        assertTrue(useCase is State.Success)
        assertEquals((useCase as State.Success).data, Unit)
    }

    @Test
    fun `validation use case failed`() = runBlockingTest {
        `when`(repository.getCharactersById(1)).thenThrow()
        val useCase = useCase(characterDto())
        assertTrue(useCase is State.Error)
    }
}