package com.globant.data.datasource.remote

import com.globant.data.mapper.CharactersListMapper
import com.globant.data.services.CharactersService
import com.globant.domain.datasource.CharactersRemoteDataSource
import com.globant.domain.model.CharactersDto
import com.globant.shared.data.network.*
import com.globant.shared.utils.extensions.*
import javax.inject.Singleton

@Singleton
class CharactersRemoteDataSourceImpl(
    private val service: CharactersService,
    private val mapper: CharactersListMapper
) : CharactersRemoteDataSource {

    companion object {
        private const val NAME_STARTS_WITH = "nameStartsWith"
        private const val LIMIT = "limit"
        private const val OFFSET = "offset"
    }

    override suspend fun getCharactersById(id: Int): State<List<CharactersDto>> = try {
        service.getCharacterById(id).create {
            mapper.mapFrom(this)
        }
    } catch (e: Throwable) {
        State.error(e.createError().value())
    }

    override fun getCharactersBySearch(
        search: String,
        limit: Int,
        offset: Int,
        listener: (State<List<CharactersDto>>) -> Unit
    ) {
        val queryMap = hashMapOf<String, Any>()
        if (search.isNotEmpty()) {
            queryMap[NAME_STARTS_WITH] = search
        }
        queryMap[LIMIT] = limit
        queryMap[OFFSET] = offset

        service.getCharactersBySearch(queryMap).enqueueResult {
            when (this) {
                is State.Success -> {
                    listener.invoke(State.success(mapper.mapFrom(data)))
                }
                is State.Error -> {
                    listener.invoke(State.error(exception ?: exception.parseError().value()))
                }
                else -> Unit
            }
        }
    }
}