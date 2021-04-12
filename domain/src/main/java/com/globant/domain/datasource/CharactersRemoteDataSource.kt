package com.globant.domain.datasource

import com.globant.domain.model.CharactersDto
import com.globant.shared.data.network.State

interface CharactersRemoteDataSource {
    suspend fun getCharactersById(id: Int): State<List<CharactersDto>>
    fun getCharactersBySearch(
        search: String,
        limit: Int,
        offset: Int,
        listener: (State<List<CharactersDto>>) -> Unit
    )
}