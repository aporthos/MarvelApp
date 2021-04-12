package com.globant.domain.datasource

import androidx.paging.DataSource
import com.globant.shared.utils.Order.OrderBy
import com.globant.domain.model.CharactersDto

interface CharactersLocalDataSource {
    fun getCharactersBySearch(
        search: String,
        @OrderBy orderBy: Int,
        isUpdate: Boolean,
        isConnectedNetwork: Boolean
    ): DataSource.Factory<Int, CharactersDto>
    fun getCharacterById(idCharacter: Int): CharactersDto?
    fun getCharactersByFavouritesIs(isFavourites: Boolean): DataSource.Factory<Int, CharactersDto>
    fun updateCharacter(character: CharactersDto): Int
    fun insertCharactersTransaction(characters: List<CharactersDto>)
}