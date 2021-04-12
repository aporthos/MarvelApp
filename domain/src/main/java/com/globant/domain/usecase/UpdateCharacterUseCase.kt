package com.globant.domain.usecase

import com.globant.domain.model.CharactersDto
import com.globant.domain.repository.CharactersRepository
import com.globant.shared.domain.di.IoDispatcher
import com.globant.shared.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateCharacterUseCase @Inject constructor(
    private val repository: CharactersRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : UseCase<CharactersDto, Unit>(ioDispatcher) {
    override suspend fun execute(parameters: CharactersDto) = repository.updateCharacter(parameters)
}