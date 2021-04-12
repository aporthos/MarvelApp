package com.globant.data.datasource.local

import androidx.paging.DataSource
import androidx.room.*
import com.globant.domain.model.CharactersDto
import com.globant.shared.utils.Order.OrderBy

@Dao
interface CharactersDao {

    companion object {
        private const val FAVOURITE_TRUE = 1
        private const val DESCENDING = 0
        private const val ASCENDING = 1
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characters: List<CharactersDto>)

    @Update
    fun update(characters: CharactersDto): Int

    @Query(
        """
            SELECT * FROM characters WHERE name LIKE '%' ||:search|| '%' ORDER BY 
            CASE WHEN :orderBy = $DESCENDING THEN name END DESC,
            CASE WHEN :orderBy = $ASCENDING THEN name END ASC
        """
    )
    fun getCharactersBySearch(
        search: String,
        @OrderBy orderBy: Int
    ): DataSource.Factory<Int, CharactersDto>

    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacterById(id: Int): CharactersDto

    @Query("SELECT * FROM characters WHERE isFavourite = :isFavourites ORDER BY name ASC")
    fun getCharactersByFavouriteIs(isFavourites: Boolean): DataSource.Factory<Int, CharactersDto>

    @Query("SELECT * FROM characters WHERE isFavourite = $FAVOURITE_TRUE")
    fun getCharactersByFavourite(): List<CharactersDto>

    @Transaction
    fun insertCharactersTransaction(characters: List<CharactersDto>) {
        val charactersFavourites = getCharactersByFavourite()
        val charactersList = if (charactersFavourites.isEmpty()) {
            characters
        } else {
            validateLocal(characters, charactersFavourites)
        }
        insert(charactersList)
    }

    @Transaction
    @Query("DELETE FROM characters")
    fun deleteCharactersAll()

    private fun validateLocal(
        charactersService: List<CharactersDto>,
        charactersFavourites: List<CharactersDto>
    ): List<CharactersDto> {
        val charactersList = mutableListOf<CharactersDto>()
        charactersList.addAll(charactersService)
        charactersFavourites.forEach { characterFavourite ->
            val character = charactersService.find { it.id == characterFavourite.id }
            character?.let {
                charactersList.remove(it)
            }
        }
        return charactersList
    }

}