package com.globant.data.repository

import androidx.paging.LivePagedListBuilder
import com.globant.domain.datasource.CharactersLocalDataSource
import com.globant.domain.datasource.CharactersRemoteDataSource
import com.globant.domain.model.CharactersDto
import com.globant.domain.model.CharactersSearchLocalResult
import com.globant.domain.model.CharactersSearchServiceResult
import com.globant.domain.repository.CharactersRepository
import com.globant.shared.data.exceptions.SelectCharacterException
import com.globant.shared.data.exceptions.UpdateException
import com.globant.shared.data.network.State
import com.globant.shared.domain.di.AppExecutors
import com.globant.shared.interfaces.NetworkUtils
import com.globant.shared.utils.Constants.CODE_ERROR
import com.globant.shared.utils.Constants.ID_INVALID_CODE
import com.globant.shared.utils.Order.OrderBy
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersRepositoryImpl @Inject constructor(
    private val charactersRemoteDataSource: CharactersRemoteDataSource,
    private val charactersLocalDataSource: CharactersLocalDataSource,
    private val networkUtils: NetworkUtils,
    private val appExecutors: AppExecutors
) : CharactersRepository {

    companion object {
        private const val DATABASE_PAGE_SIZE = 30
        private const val UPDATE_FAILED = 0
    }

    override fun getCharactersBySearch(search: String, @OrderBy orderBy: Int, isUpdate: Boolean): CharactersSearchServiceResult {
        val dataSourceFactory =
            charactersLocalDataSource.getCharactersBySearch(search, orderBy, isUpdate, networkUtils.hasNetworkConnection())

        val repoBoundaryCallback = CharactersPagedListCallback(
            charactersLocalDataSource,
            charactersRemoteDataSource,
            search,
            networkUtils.hasNetworkConnection(),
            appExecutors.diskIO())

        return CharactersSearchServiceResult(dataSourceFactory, repoBoundaryCallback)
    }

    override fun getCharactersByFavouriteIs(isFavourites: Boolean): CharactersSearchLocalResult {
        val dataSourceFactory = charactersLocalDataSource.getCharactersByFavouritesIs(isFavourites)
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE).build()
        return CharactersSearchLocalResult(data)
    }

    override fun getCharactersById(idCharacter: Int): CharactersDto {
        val result = charactersLocalDataSource.getCharacterById(idCharacter)
        return result ?: throw SelectCharacterException(CODE_ERROR, ID_INVALID_CODE)
    }

    override suspend fun  getCharactersRemoteById(idCharacter: Int): State<List<CharactersDto>> {
        val response = charactersRemoteDataSource.getCharactersById(idCharacter)
        if (response is State.Success && response.data.isNotEmpty()) {
            charactersLocalDataSource.updateCharacter(response.data.first())
        }
        return response
    }

    override fun updateCharacter(character: CharactersDto) {
        val result = charactersLocalDataSource.updateCharacter(character)
        if (result == UPDATE_FAILED) {
            throw UpdateException(CODE_ERROR, ID_INVALID_CODE)
        }
    }
}