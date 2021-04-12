package com.globant.data.datasource.local

import androidx.paging.DataSource
import com.globant.domain.datasource.CharactersLocalDataSource
import com.globant.domain.model.CharactersDto
import com.globant.shared.utils.Order
import javax.inject.Singleton

@Singleton
class CharactersLocalDataSourceImpl(
    private val dao: CharactersDao
) : CharactersLocalDataSource {

    override fun getCharactersBySearch(
        search: String,
        @Order.OrderBy orderBy: Int,
        isUpdate: Boolean,
        isConnectedNetwork: Boolean
    ): DataSource.Factory<Int, CharactersDto>  {
        if (isConnectedNetwork && isUpdate) {
            dao.deleteCharactersAll()
        }
        return dao.getCharactersBySearch(search, orderBy)
    }

    override fun getCharacterById(idCharacter: Int): CharactersDto? = dao.getCharacterById(idCharacter)

    override fun getCharactersByFavouritesIs(isFavourites: Boolean): DataSource.Factory<Int, CharactersDto> =
        dao.getCharactersByFavouriteIs(isFavourites)

    override fun updateCharacter(character: CharactersDto): Int =
        dao.update(character)

    override fun insertCharactersTransaction(characters: List<CharactersDto>) {
        dao.insertCharactersTransaction(characters)
    }
}