package com.globant.domain.usecase

import androidx.paging.DataSource
import androidx.paging.PagedList
import com.globant.domain.model.CharactersDto
import com.globant.domain.model.CharactersSearchServiceResult
import com.globant.domain.repository.CharactersRepository
import com.globant.shared.data.network.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.StrictStubs::class)
class GetCharactersByAllUseCaseTest {
    @Mock
    private lateinit var repository: CharactersRepository

    private val useCase by lazy {
        GetCharactersByAllUseCase(repository, TestCoroutineDispatcher())
    }

    @Test
    fun `validation use case success`() = runBlockingTest {
        val callback =
            mock(PagedList.BoundaryCallback::class.java) as PagedList.BoundaryCallback<CharactersDto>
        val dataSource =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, CharactersDto>
        val result = CharactersSearchServiceResult(dataSource, callback)
        val parameters = GetCharactersByAllUseCase.Parameters("", 1, false)
        `when`(repository.getCharactersBySearch("", 1, false)).thenReturn(result)
        val useCase = useCase(parameters)
        assertTrue(useCase is State.Success)
    }

    @Test
    fun `validation use case failed`() = runBlockingTest {
        `when`(repository.getCharactersByFavouriteIs(true)).thenThrow()
        val parameters = GetCharactersByAllUseCase.Parameters("", 1, false)
        val useCase = useCase(parameters)
        assertTrue(useCase is State.Error)
    }


}