package com.globant.domain.repository

import com.globant.domain.model.CharactersDto
import com.globant.shared.utils.Order.OrderBy
import com.globant.domain.model.CharactersSearchLocalResult
import com.globant.domain.model.CharactersSearchServiceResult
import com.globant.shared.data.network.State

interface CharactersRepository {
    fun updateCharacter(character: CharactersDto)
    fun getCharactersById(idCharacter: Int): CharactersDto
    suspend fun getCharactersRemoteById(idCharacter: Int): State<List<CharactersDto>>
    fun getCharactersBySearch(search: String, @OrderBy orderBy: Int, isUpdate: Boolean): CharactersSearchServiceResult
    fun getCharactersByFavouriteIs(isFavourites: Boolean): CharactersSearchLocalResult
}