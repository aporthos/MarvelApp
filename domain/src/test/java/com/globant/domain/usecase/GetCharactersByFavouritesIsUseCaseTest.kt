package com.globant.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.globant.domain.model.CharactersDto
import com.globant.domain.model.CharactersSearchLocalResult
import com.globant.domain.repository.CharactersRepository
import com.globant.shared.data.network.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.StrictStubs::class)
class GetCharactersByFavouritesIsUseCaseTest {
    @Mock
    private lateinit var repository: CharactersRepository

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    private val useCase by lazy {
        GetCharactersByFavouritesIsUseCase(
            repository,
            TestCoroutineDispatcher()
        )
    }

    @Test
    fun `validation use case success`() = runBlockingTest {
        val callback = mock(PagedList::class.java) as PagedList<CharactersDto>
        val pagedlist = MutableLiveData<PagedList<CharactersDto>>()
        pagedlist.value = callback
        val result = CharactersSearchLocalResult(pagedlist)
        `when`(repository.getCharactersByFavouriteIs(true)).thenReturn(result)
        val parameters = GetCharactersByFavouritesIsUseCase.Parameters(true)
        val useCase = useCase(parameters)
        assertTrue(useCase is State.Success)
    }

    @Test
    fun `validation use case failed`() = runBlockingTest {
        `when`(repository.getCharactersByFavouriteIs(true)).thenThrow()
        val parameters = GetCharactersByFavouritesIsUseCase.Parameters(true)
        val useCase = useCase(parameters)
        assertTrue(useCase is State.Error)
    }
}