package com.globant.domain.usecase

import com.globant.domain.model.CharactersSearchLocalResult
import com.globant.domain.repository.CharactersRepository
import com.globant.shared.domain.di.IoDispatcher
import com.globant.shared.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCharactersByFavouritesIsUseCase @Inject constructor(
    private val repository: CharactersRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : UseCase<GetCharactersByFavouritesIsUseCase.Parameters, CharactersSearchLocalResult>(
    ioDispatcher
) {

    override suspend fun execute(parameters: Parameters): CharactersSearchLocalResult =
        repository.getCharactersByFavouriteIs(parameters.isFavourites)

    data class Parameters(val isFavourites: Boolean)
}