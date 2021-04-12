package com.globant.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.domain.MockFactory
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
class GetCharactersRemoteByIdUseCaseTest {

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: CharactersRepository

    private val useCase by lazy { GetCharactersRemoteByIdUseCase(repository, TestCoroutineDispatcher()) }

    @Test
    fun `validation use case success`() = runBlockingTest {
        `when`(repository.getCharactersRemoteById(1)).thenReturn(State.success(listOf(
            MockFactory.characterDto())))
        val useCase = useCase(1)
        assertTrue(useCase is State.Success)
        assertEquals((useCase as State.Success).data, listOf(MockFactory.characterDto()))
    }

    @Test
    fun `validation use case failed`() = runBlockingTest {
        `when`(repository.getCharactersRemoteById(1)).thenReturn(State.error(""))
        val useCase = useCase(1)
        assertTrue(useCase is State.Error)
    }

}