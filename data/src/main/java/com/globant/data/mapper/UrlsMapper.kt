package com.globant.data.mapper

import com.globant.data.models.remote.response.UrlsEntity
import com.globant.domain.model.UrlsDto
import com.globant.shared.data.mapper.Mapper
import com.globant.shared.utils.extensions.value
import javax.inject.Inject

class UrlsMapper @Inject constructor() : Mapper<UrlsEntity, UrlsDto>() {
    override fun mapFrom(from: UrlsEntity): UrlsDto =
        UrlsDto(
            type = from.type.value(),
            url = from.url.value()
        )
}