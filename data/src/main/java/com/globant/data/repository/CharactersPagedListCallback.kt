package com.globant.data.repository

import androidx.paging.PagedList
import com.globant.domain.model.CharactersDto
import com.globant.shared.data.network.State
import java.util.concurrent.Executor

class CharactersPagedListCallback(
    private val charactersLocalDataSource: com.globant.domain.datasource.CharactersLocalDataSource,
    private val charactersRemoteDataSource: com.globant.domain.datasource.CharactersRemoteDataSource,
    private val search: String,
    private val isConnectedNetwork: Boolean,
    private val ioExecutor: Executor
) :
    PagedList.BoundaryCallback<CharactersDto>() {

    companion object {
        private const val LIMIT_REQUEST = 50
        private const val INCREMENT_OFFSET = 50
    }

    private var lastRequestedPage = 0

    private var isRequestInProgress = false

    override fun onItemAtEndLoaded(itemAtEnd: CharactersDto) {
        requestAndSaveData()
    }

    override fun onZeroItemsLoaded() {
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) {
            return
        }

        if (!isConnectedNetwork) {
            return
        }

        isRequestInProgress = true
        charactersRemoteDataSource.getCharactersBySearch(search, LIMIT_REQUEST, lastRequestedPage) {
            when (it) {
                is State.Success -> {
                    ioExecutor.execute {
                        if (it.data.isNotEmpty()) {
                            charactersLocalDataSource.insertCharactersTransaction(it.data)
                        }
                        lastRequestedPage += INCREMENT_OFFSET
                        isRequestInProgress = false
                    }
                }
                is State.Error -> {

                    isRequestInProgress = false
                }
                else -> Unit
            }
        }
    }
}