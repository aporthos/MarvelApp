package com.globant.shared.data.network

sealed class State<out R> {
    data class Success<out T>(val data: T) : State<T>()
    object Loading : State<Nothing>()
    data class Error<out T>(val exception: String? = null) : State<T>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }

    companion object {
        fun <T> success(value: T): State<T> = Success(value)
        fun <T> error(exception: String): State<T> = Error(exception)
    }

    val State<*>.succeeded
        get() = this is Success && data != null
}