package com.globant.domain.usecase

import com.globant.domain.model.CharactersDto
import com.globant.domain.repository.CharactersRepository
import com.globant.shared.data.network.State
import com.globant.shared.domain.di.IoDispatcher
import com.globant.shared.domain.usecase.RemoteUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCharactersRemoteByIdUseCase @Inject constructor(
    private val repository: CharactersRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : RemoteUseCase<Int, State<List<CharactersDto>>>(ioDispatcher) {
    override suspend fun execute(parameters: Int): State<List<CharactersDto>> =
        repository.getCharactersRemoteById(parameters)
}