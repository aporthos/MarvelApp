package com.globant.marvelapp.ui.characters.favourites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.globant.domain.usecase.GetCharactersByFavouritesIsUseCase
import com.globant.domain.model.CharactersDto
import com.globant.domain.usecase.UpdateCharacterUseCase
import com.globant.shared.data.network.State
import kotlinx.coroutines.launch

class FavouritesViewModel @ViewModelInject constructor(
    private val getCharactersByFavouritesIsUseCase: GetCharactersByFavouritesIsUseCase,
    private val updateCharacterUseCase: UpdateCharacterUseCase
) : ViewModel(), LifecycleObserver {

    companion object {
        private const val FAVOURITE_FALSE = false
    }

    private var _charactersList = MediatorLiveData<PagedList<CharactersDto>>()
    val charactersList: LiveData<PagedList<CharactersDto>>
        get() = _charactersList

    init {
        initSearch()
    }

    private fun initSearch() = viewModelScope.launch {
        val params = GetCharactersByFavouritesIsUseCase.Parameters(true)
        val favouritesResult = getCharactersByFavouritesIsUseCase(params)
        if (favouritesResult is State.Success) {
            _charactersList.addSource(favouritesResult.data.data) {
                _charactersList.value = it
            }
        }
    }

    fun toUpdateCharacter(charactersDto: CharactersDto) {
        viewModelScope.launch {
            val characterFavourite = charactersDto.copy(isFavourite = FAVOURITE_FALSE)
            updateCharacterUseCase(characterFavourite)
        }
    }
}