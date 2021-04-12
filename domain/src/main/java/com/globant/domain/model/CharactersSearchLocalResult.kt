package com.globant.domain.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class CharactersSearchLocalResult(
    val data: LiveData<PagedList<CharactersDto>>
)