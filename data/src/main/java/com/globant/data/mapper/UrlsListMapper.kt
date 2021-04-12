package com.globant.data.mapper

import com.globant.data.models.remote.response.UrlsEntity
import com.globant.domain.model.UrlsDto
import com.globant.shared.data.mapper.Mapper
import javax.inject.Inject

class UrlsListMapper @Inject constructor(val mapper: UrlsMapper) :
    Mapper<List<UrlsEntity>?, List<UrlsDto>>() {
    override fun mapFrom(from: List<UrlsEntity>?): List<UrlsDto> = mutableListOf<UrlsDto>().apply {
        from?.map {
            add(mapper.mapFrom(it))
        }
    }
}