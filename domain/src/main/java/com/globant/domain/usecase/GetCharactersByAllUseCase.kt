package com.globant.domain.usecase

import com.globant.domain.model.CharactersSearchServiceResult
import com.globant.domain.repository.CharactersRepository
import com.globant.shared.domain.di.IoDispatcher
import com.globant.shared.domain.usecase.UseCase
import com.globant.shared.utils.Order.OrderBy
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCharactersByAllUseCase @Inject constructor(
    private val repository: CharactersRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : UseCase<GetCharactersByAllUseCase.Parameters, CharactersSearchServiceResult>(
    ioDispatcher
) {

    override suspend fun execute(parameters: Parameters): CharactersSearchServiceResult =
        repository.getCharactersBySearch(parameters.search, parameters.orderBy, parameters.isUpdate)

    data class Parameters(val search: String, @OrderBy val orderBy: Int, val isUpdate: Boolean)
}
