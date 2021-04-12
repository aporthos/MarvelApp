package com.globant.marvelapp.ui.characters.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.globant.marvelapp.ui.characters.MockFactory
import com.globant.domain.model.CharactersDto
import com.globant.domain.usecase.GetCharactersByAllUseCase
import com.globant.domain.usecase.UpdateCharacterUseCase
import com.globant.domain.model.CharactersSearchServiceResult
import com.globant.marvelapp.R
import com.globant.marvelapp.ui.characters.getThisValue
import com.globant.marvelapp.ui.characters.rule.MainCoroutineRule
import com.globant.marvelapp.ui.characters.rule.runBlockingTest
import com.globant.shared.data.network.State
import com.globant.shared.interfaces.NetworkUtils
import com.globant.shared.utils.Order.ASCENDING
import com.globant.shared.utils.Order.DESCENDING
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.StrictStubs::class)
class CharactersViewModelTest {

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var updateCharacterUseCase: UpdateCharacterUseCase

    @Mock
    private lateinit var getCharactersByAllUseCase: GetCharactersByAllUseCase

    @Mock
    private lateinit var networkUtils: NetworkUtils

    private val viewModel by lazy {
        CharactersViewModel(
            updateCharacterUseCase,
            getCharactersByAllUseCase,
            networkUtils,
            mainCoroutineRule.testDispatcher
        )
    }

    @Test
    fun `Validate search success`() = mainCoroutineRule.runBlockingTest {
        mainCoroutineRule.testDispatcher.pauseDispatcher()
        val dataSourcer =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, CharactersDto>
        val callback =
            mock(PagedList.BoundaryCallback::class.java) as PagedList.BoundaryCallback<CharactersDto>
        val result = State.Success(
            CharactersSearchServiceResult(
                dataSourcer,
                callback
            )
        )
        (viewModel).apply {
            isUpdate.value = true
        }
        val parameters = GetCharactersByAllUseCase.Parameters("", 1, true)
        `when`(getCharactersByAllUseCase(parameters)).thenReturn(result)
        viewModel.toSearchCharacters()
        assertEquals(viewModel.loading.getThisValue(), true)
        mainCoroutineRule.testDispatcher.resumeDispatcher()
        assertEquals(viewModel.loading.getThisValue(), false)
    }


    @Test
    fun `Validate search error`() = mainCoroutineRule.runBlockingTest {
        mainCoroutineRule.testDispatcher.pauseDispatcher()
        toCreateError()
        viewModel.toSearchCharacters()
        assertEquals(viewModel.loading.getThisValue(), true)
        mainCoroutineRule.testDispatcher.resumeDispatcher()
        assertEquals(viewModel.loading.getThisValue(), false)
        assertEquals(viewModel.messageError.getThisValue(), R.string.message_invalid_credential)
    }

    @Test
    fun `Validate update character`() = mainCoroutineRule.runBlockingTest {
        val character = MockFactory.characterDto()
        viewModel.toUpdateCharacter(character)
        val characterFavourite = character.copy(isFavourite = true)
        verify(updateCharacterUseCase).invoke(characterFavourite)
    }

    @Test
    fun `Validate update refresh`() = mainCoroutineRule.runBlockingTest {
        mainCoroutineRule.testDispatcher.pauseDispatcher()
        val parameters = GetCharactersByAllUseCase.Parameters("", 1, true)
        viewModel.refresh()
        assertEquals(viewModel.search.getThisValue(), "")
        assertEquals(viewModel.order.getThisValue(), ASCENDING)
        assertEquals(viewModel.loading.getThisValue(), true)
        mainCoroutineRule.testDispatcher.resumeDispatcher()
        verify(getCharactersByAllUseCase).invoke(parameters)
    }

    @Test
    fun `Validate message error when is InvalidCredentials`() {
        toCreateError()
        viewModel.toSearchCharacters()
        assertEquals(viewModel.messageError.getThisValue(), R.string.message_invalid_credential)
    }

    @Test
    fun `Validate message error when is Missing Parameter`() {
        toCreateError("MissingParameter")
        viewModel.toSearchCharacters()
        assertEquals(
            viewModel.messageError.getThisValue(),
            R.string.message_invalid_missing_parameter
        )
    }

    @Test
    fun `Validate message error when is NoInternet`() {
        toCreateError("NoInternet")
        viewModel.toSearchCharacters()
        assertEquals(
            viewModel.messageError.getThisValue(),
            R.string.message_no_internet
        )
    }

    @Test
    fun `Validate message error when Unknow`() {
        toCreateError("Unknow")
        viewModel.toSearchCharacters()
        assertEquals(
            viewModel.messageError.getThisValue(),
            R.string.message_error_unknow
        )
    }

    @Test
    fun `Validate message order by`() {
        val observer = mock<Observer<Int>>()
        viewModel.order.observeForever(observer)
        viewModel.toSearchCharacters()
        verify(observer).onChanged(DESCENDING)
    }

    @Test
    fun `Validate message order by descending`() = mainCoroutineRule.runBlockingTest {
        val observer = mock<Observer<Int>>() {
            DESCENDING
        }
        viewModel.order.observeForever(observer)
        viewModel.toSearchCharacters()
        verify(observer).onChanged(ASCENDING)
    }

    private fun toCreateError(messageCode: String = "InvalidCredentials") =
        mainCoroutineRule.runBlockingTest {
            val result = State.Error<CharactersSearchServiceResult>(messageCode)
            val parameters = GetCharactersByAllUseCase.Parameters("", 1, false)
            `when`(getCharactersByAllUseCase.invoke(parameters)).thenReturn(result)
        }
}