package com.globant.marvelapp.ui.characters.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.globant.domain.model.CharactersDto
import com.globant.domain.usecase.GetCharactersByIdUseCase
import com.globant.domain.usecase.GetCharactersRemoteByIdUseCase
import com.globant.domain.usecase.UpdateCharacterUseCase
import com.globant.marvelapp.ui.characters.MockFactory.characterDto
import com.globant.marvelapp.ui.characters.MockFactory.listUrl
import com.globant.marvelapp.ui.characters.getThisValue
import com.globant.marvelapp.ui.characters.rule.MainCoroutineRule
import com.globant.marvelapp.ui.characters.rule.runBlockingTest
import com.globant.shared.data.network.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.never
import org.mockito.kotlin.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.StrictStubs::class)
class CharacterDetailViewModelTest {
    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var getCharactersByIdUseCase: GetCharactersByIdUseCase

    @Mock
    private lateinit var updateCharacterUseCase: UpdateCharacterUseCase

    @Mock
    private lateinit var getCharactersRemoteByIdUseCase: GetCharactersRemoteByIdUseCase

    private val viewModel by lazy {
        CharacterDetailViewModel(
            getCharactersByIdUseCase,
            updateCharacterUseCase,
            getCharactersRemoteByIdUseCase,
            mainCoroutineRule.testDispatcher
        )
    }

    @Test
    fun `Validate search with id`() = mainCoroutineRule.runBlockingTest {
        mainCoroutineRule.testDispatcher.pauseDispatcher()
        val result = State.Success(characterDto())
        `when`(getCharactersByIdUseCase(1)).thenReturn(result)
        viewModel.initSearch(1)
        assertEquals(viewModel.loading.getThisValue(), true)
        mainCoroutineRule.testDispatcher.resumeDispatcher()
        assertEquals(viewModel.loading.getThisValue(), false)
        assertNotNull(viewModel.character.value)
        verify(getCharactersByIdUseCase).invoke(1)
    }

    @Test
    fun `Validate search with id failed`() = mainCoroutineRule.runBlockingTest {
        mainCoroutineRule.testDispatcher.pauseDispatcher()
        val result = State.Error<CharactersDto>("")
        `when`(getCharactersByIdUseCase(1)).thenReturn(result)
        viewModel.initSearch(1)
        assertEquals(viewModel.loading.getThisValue(), true)
        mainCoroutineRule.testDispatcher.resumeDispatcher()
        assertEquals(viewModel.loading.getThisValue(), false)
        assertNull(viewModel.character.value)
        verify(getCharactersByIdUseCase).invoke(1)
    }

    @Test
    fun `Validate save character`() = mainCoroutineRule.runBlockingTest {
        val characterSave = characterDto()
        mainCoroutineRule.testDispatcher.pauseDispatcher()
        `when`(updateCharacterUseCase(characterSave.apply { isFavourite = true })).thenReturn(State.success(Unit))
        (viewModel).apply {
            character.value = characterSave
        }
        viewModel.saveCharacter()
        assertEquals(viewModel.loading.getThisValue(), true)
        mainCoroutineRule.testDispatcher.resumeDispatcher()
        assertEquals(viewModel.loading.getThisValue(), false)
    }

    @Test
    fun `Validate refresh character`() = mainCoroutineRule.runBlockingTest {
        mainCoroutineRule.testDispatcher.pauseDispatcher()
        val result = State.Success(listOf(characterDto()))
        `when`(getCharactersRemoteByIdUseCase(1)).thenReturn(result)
        viewModel.refresh(1)
        assertEquals(viewModel.loading.getThisValue(), true)
        mainCoroutineRule.testDispatcher.resumeDispatcher()
        verify(getCharactersByIdUseCase).invoke(1011334)
    }

    @Test
    fun `Validate refresh character empty`() = mainCoroutineRule.runBlockingTest {
        mainCoroutineRule.testDispatcher.pauseDispatcher()
        val result = State.Success(listOf<CharactersDto>())
        `when`(getCharactersRemoteByIdUseCase(1)).thenReturn(result)
        val observer = mock<Observer<Int>>() {}
        viewModel.messageError.observeForever(observer)
        viewModel.refresh(1)
        assertEquals(viewModel.loading.getThisValue(), true)
        mainCoroutineRule.testDispatcher.resumeDispatcher()
        assertEquals(viewModel.loading.getThisValue(), false)
        verify(getCharactersByIdUseCase, never()).invoke(1011334)
        verify(observer).onChanged(any())
        viewModel.messageError.removeObserver(observer)
    }

    @Test
    fun `Validate refresh character failed`() = mainCoroutineRule.runBlockingTest {
        mainCoroutineRule.testDispatcher.pauseDispatcher()
        val result = State.Error<List<CharactersDto>>("")
        `when`(getCharactersRemoteByIdUseCase(1)).thenReturn(result)
        val observer = mock<Observer<Int>>() {}
        viewModel.messageError.observeForever(observer)
        viewModel.refresh(1)
        assertEquals(viewModel.loading.getThisValue(), true)
        mainCoroutineRule.testDispatcher.resumeDispatcher()
        assertEquals(viewModel.loading.getThisValue(), false)
        verify(observer).onChanged(any())
        viewModel.messageError.removeObserver(observer)
    }

    @Test
    fun `Validate save character failed`() = mainCoroutineRule.runBlockingTest {
        val characterSave = characterDto()

        mainCoroutineRule.testDispatcher.pauseDispatcher()
        `when`(updateCharacterUseCase(characterSave.apply { isFavourite = true })).thenReturn(State.Error())
        (viewModel).apply {
            character.value = characterSave
        }
        val observer = mock<Observer<Int>>() {}
        viewModel.messageError.observeForever(observer)
        viewModel.saveCharacter()
        assertEquals(viewModel.loading.getThisValue(), true)
        mainCoroutineRule.testDispatcher.resumeDispatcher()
        assertEquals(viewModel.loading.getThisValue(), false)

        verify(observer).onChanged(any())
        viewModel.messageError.removeObserver(observer)
    }

    @Test
    fun `Validate onClick when is detail`() {
        viewModel._urls.value = listUrl()
        viewModel.onClick("detail")
        assertEquals(
            viewModel.link.getThisValue(),
            "http://marvel.com/characters/76/a-bomb?utm_campaign=apiRef&utm_source=eb8202d08b009675a700b4bfd597d066"
        )
    }

    @Test
    fun `Validate onClick when is wiki`() {
        viewModel._urls.value = listUrl()
        viewModel.onClick("wiki")
        assertEquals(
            viewModel.link.getThisValue(),
            "http://marvel.com/universe/A.I.M.?utm_campaign=apiRef&utm_source=eb8202d08b009675a700b4bfd597d066"
        )
    }

    @Test
    fun `Validate onClick when is comiclink`() {
        viewModel._urls.value = listUrl()
        viewModel.onClick("comiclink")
        assertEquals(
            viewModel.link.getThisValue(),
            "http://marvel.com/comics/characters/1017100/a-bomb_has?utm_campaign=apiRef&utm_source=eb8202d08b009675a700b4bfd597d066"
        )
    }
}