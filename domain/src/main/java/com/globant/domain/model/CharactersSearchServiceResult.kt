package com.globant.domain.model

import androidx.paging.DataSource
import androidx.paging.PagedList

data class CharactersSearchServiceResult(
    val dataSource: DataSource.Factory<Int, CharactersDto>,
    val boundaryCallback: PagedList.BoundaryCallback<CharactersDto>
)