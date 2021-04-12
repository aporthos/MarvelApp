package com.globant.marvelapp.ui.characters.favourites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.domain.usecase.GetCharactersByFavouritesIsUseCase
import com.globant.domain.usecase.UpdateCharacterUseCase
import com.globant.marvelapp.ui.characters.MockFactory
import com.globant.marvelapp.ui.characters.rule.MainCoroutineRule
import com.globant.marvelapp.ui.characters.rule.runBlockingTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.StrictStubs::class)
class FavouritesViewModelTest {

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var getCharactersByFavouritesIsUseCase: GetCharactersByFavouritesIsUseCase

    @Mock
    private lateinit var updateCharacterUseCase: UpdateCharacterUseCase

    private lateinit var viewModel: FavouritesViewModel

    @Before
    fun setup() {
        viewModel = FavouritesViewModel(getCharactersByFavouritesIsUseCase, updateCharacterUseCase)
    }

    @Test
    fun `Validate load favourites`() = mainCoroutineRule.runBlockingTest {
        val parameters = GetCharactersByFavouritesIsUseCase.Parameters(true)
        verify(getCharactersByFavouritesIsUseCase).invoke(parameters)
    }

    @Test
    fun `Validate update character`() = mainCoroutineRule.runBlockingTest {
        val character = MockFactory.characterDto()
        viewModel.toUpdateCharacter(character)
        val characterFavourite = character.copy(isFavourite = false)
        verify(updateCharacterUseCase).invoke(characterFavourite)
    }

}