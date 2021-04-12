package com.globant.data.mapper

import com.globant.data.models.remote.response.CharactersResponse
import com.globant.domain.model.CharactersDto
import com.globant.shared.data.mapper.Mapper
import com.globant.shared.data.network.ApiCommon
import javax.inject.Inject

class CharactersListMapper @Inject constructor(private val mapper: CharactersMapper) :
    Mapper<ApiCommon<CharactersResponse>, List<CharactersDto>>() {
    override fun mapFrom(from: ApiCommon<CharactersResponse>): List<CharactersDto> =
        mutableListOf<CharactersDto>().apply {
            from.data?.results?.map {
                mapper.mapFrom(it)?.let { character ->
                    add(character)
                }
            }
        }
}