package com.globant.shared.domain.usecase

import com.globant.shared.data.network.State
import com.globant.shared.utils.extensions.createError
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineDispatcher
import timber.log.Timber
import kotlin.jvm.Throws

abstract class UseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(parameters: P): State<R> {
        return try {
            withContext(coroutineDispatcher) {
                execute(parameters).let {
                    State.Success(it)
                }
            }
        } catch (e: Exception) {
            Timber.i("invoke: $e")
            State.Error(e.createError())
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}