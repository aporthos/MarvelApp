package com.globant.marvelapp.ui.characters.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.globant.domain.model.CharactersDto
import com.globant.domain.usecase.GetCharactersByAllUseCase
import com.globant.domain.usecase.UpdateCharacterUseCase
import com.globant.shared.utils.Order.OrderBy
import com.globant.shared.utils.Order.ASCENDING
import com.globant.marvelapp.ui.common.BaseViewModel
import com.globant.shared.data.network.State
import com.globant.shared.domain.di.MainDispatcher
import com.globant.shared.interfaces.NetworkUtils
import com.globant.shared.utils.Order.DESCENDING
import com.globant.shared.utils.extensions.value
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class CharactersViewModel @ViewModelInject constructor(
    private val updateCharacterUseCase: UpdateCharacterUseCase,
    private val getCharactersByAllUseCase: GetCharactersByAllUseCase,
    private val networkUtils: NetworkUtils,
    @MainDispatcher val ioDispatcher: CoroutineDispatcher
) :
    BaseViewModel(), LifecycleObserver {

    companion object {
        private const val EMPTY = ""
        private const val DATABASE_PAGE_SIZE = 30
        private const val PREFETCH_DISTANCE = 7
    }

    private val _characterListMediator = MutableLiveData<LiveData<PagedList<CharactersDto>>>()

    val characterList: LiveData<PagedList<CharactersDto>>
        get() = Transformations.switchMap(_characterListMediator) { it }

    val messageErrorInternet: LiveData<Boolean>
        get() = Transformations.map(characterList) {
            return@map it.isEmpty() && !networkUtils.hasNetworkConnection()
        }

    private val _messageError = MutableLiveData<Int>()
    val messageError: LiveData<Int>
        get() = _messageError

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _search = MutableLiveData<String>()
    val search: LiveData<String>
        get() = _search

    private var _order = MutableLiveData<@OrderBy Int>(ASCENDING)
    val order: LiveData<Int>
        get() = _order

    val isUpdate = MutableLiveData<Boolean>(false)

    init {
        toSearchCharacters()
    }

    private val pagedListConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(DATABASE_PAGE_SIZE)
        .setPrefetchDistance(PREFETCH_DISTANCE)
        .setInitialLoadSizeHint(DATABASE_PAGE_SIZE)
        .build()

    fun toSearchCharacters() {
        _loading.value = true
        viewModelScope.launch(ioDispatcher) {
            val parameters =
                GetCharactersByAllUseCase.Parameters(
                    _search.value.value(),
                    _order.value ?: ASCENDING,
                    isUpdate.value ?: false
                )
            val charactersResult = getCharactersByAllUseCase(parameters)
            when (charactersResult) {
                is State.Success -> {
                    _loading.value = false
                    val resource = charactersResult.data
                    _characterListMediator.postValue(
                        LivePagedListBuilder(resource.dataSource, pagedListConfig)
                            .setBoundaryCallback(resource.boundaryCallback)
                            .build()
                    )
                }
                is State.Error -> {
                    _loading.value = false
                    _messageError.value = toGetMessageError(charactersResult.exception)
                }
                State.Loading -> Unit
            }
            toCreateOrderBy(_order.value ?: ASCENDING)
        }
    }

    fun toUpdateCharacter(character: CharactersDto) {
        viewModelScope.launch {
            val characterFavourite = character.copy(isFavourite = !character.isFavourite)
            updateCharacterUseCase(characterFavourite)
        }
    }

    fun refresh() {
        _search.value = EMPTY
        _order.value = ASCENDING
        isUpdate.value = true
        toSearchCharacters()
    }

    fun search(search: String) {
        _search.value = search
        toSearchCharacters()
    }

    private fun toCreateOrderBy(@OrderBy orderBy: Int) {
        if (orderBy == DESCENDING) {
            _order.value = ASCENDING
        } else if (orderBy == ASCENDING) {
            _order.value = DESCENDING
        }
    }
}
