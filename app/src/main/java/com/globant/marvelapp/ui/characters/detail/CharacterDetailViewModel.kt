package com.globant.marvelapp.ui.characters.detail

import androidx.annotation.VisibleForTesting
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.globant.domain.model.CharactersDto
import com.globant.domain.model.UrlsDto
import com.globant.domain.usecase.GetCharactersByIdUseCase
import com.globant.domain.usecase.GetCharactersRemoteByIdUseCase
import com.globant.domain.usecase.UpdateCharacterUseCase
import com.globant.marvelapp.ui.common.BaseViewModel
import com.globant.shared.data.network.State
import com.globant.marvelapp.R
import com.globant.shared.domain.di.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class CharacterDetailViewModel @ViewModelInject constructor(
    private val getCharactersByIdUseCase: GetCharactersByIdUseCase,
    private val updateCharacterUseCase: UpdateCharacterUseCase,
    private val getCharactersRemoteByIdUseCase: GetCharactersRemoteByIdUseCase,
    @MainDispatcher val ioDispatcher: CoroutineDispatcher
) : BaseViewModel(), LifecycleObserver {

    companion object {
        private const val FAVOURITE_TRUE = true
    }

    val character = MutableLiveData<CharactersDto>().apply { value = null }
    //val character: LiveData<CharactersDto> = _character

    @VisibleForTesting
    var _urls = MutableLiveData<List<UrlsDto>>()

    private val _link = MutableLiveData<String?>()
    val link: LiveData<String?>
        get() = _link

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _messageError = MutableLiveData<Int>()
    val messageError: LiveData<Int>
        get() = _messageError

    fun initSearch(idCharacter: Int) {
        _loading.value = true
        viewModelScope.launch(ioDispatcher) {
            val characterResult = getCharactersByIdUseCase(idCharacter)
            if (characterResult is State.Success) {
                _loading.value = false
                character.value = characterResult.data
                _urls.value = characterResult.data.urls
            } else if (characterResult is State.Error) {
                _loading.value = false
                _messageError.value = toGetMessageError(characterResult.exception)
            }
        }
    }

    fun saveCharacter() {
        _loading.value = true
        viewModelScope.launch(ioDispatcher) {
            character.value?.let {
                val characterFavourite = it.copy(isFavourite = FAVOURITE_TRUE)
                val characterResult = updateCharacterUseCase(characterFavourite)
                if (characterResult is State.Success) {
                    initSearch(characterFavourite.id)
                    _loading.value = false
                } else if (characterResult is State.Error) {
                    _loading.value = false
                    _messageError.value = toGetMessageError(characterResult.exception)
                }
            }
        }
    }

    fun onClick(type: String) {
        val resultLink = _urls.value?.find { it.type == type }
        _link.value = resultLink?.url
    }

    fun refresh(idCharacter: Int) {
        _loading.value = true
        viewModelScope.launch {
            val characterResult = getCharactersRemoteByIdUseCase(idCharacter)
            if (characterResult is State.Success) {
                _loading.value = false
                if (characterResult.data.isNotEmpty()) {
                    initSearch(characterResult.data.first().id)
                } else {
                    _messageError.value = R.string.character_detail_fragment_id_invalid
                }
            } else if (characterResult is State.Error) {
                _loading.value = false
                _messageError.value = toGetMessageError(characterResult.exception)
            }
        }
    }
}