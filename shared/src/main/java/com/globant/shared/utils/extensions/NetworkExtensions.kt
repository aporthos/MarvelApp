package com.globant.shared.utils.extensions

import com.globant.shared.data.exceptions.SelectCharacterException
import com.globant.shared.data.network.State
import com.globant.shared.utils.Constants.JSON_INVALID_CODE
import com.globant.shared.utils.Constants.NO_INTERNET_CODE
import com.google.gson.stream.MalformedJsonException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException

fun Throwable.createError(): String? {
    return when (this) {
        is ConnectException, is UnknownHostException -> {
            NO_INTERNET_CODE
        }
        is HttpException -> {
            response()?.errorBody()?.string().parseErrorBody()?.code
        }
        is MalformedJsonException -> {
            JSON_INVALID_CODE
        }
        is SelectCharacterException -> {
            message
        }
        else -> {
            message
        }
    }
}

fun <R, T> Response<R>.create(action: R.() -> T): State<T> {
    return if (isSuccessful) {
        body()?.run {
            State.success(action())
        } ?: State.error(message())
    } else {
        val msg = errorBody()?.string()
        val errorMsg = if (msg.isNullOrEmpty()) {
            message()
        } else {
            msg
        }
        State.error(errorMsg.parseError() ?: errorMsg)
    }
}

fun <T> Response<T>.result(): State<T> {
    if (isSuccessful) {
        val body = body()
        return if (body != null) {
            State.Success(body)
        } else {
            State.Error(message())
        }
    } else {
        val msg = errorBody()?.string()
        val errorMsg = if (msg.isNullOrEmpty()) {
            message()
        } else {
            msg
        }
        return State.Error(errorMsg.parseError() ?: errorMsg)
    }
}

inline fun <T> Call<T>.enqueueResult(crossinline onResult: State<T>.() -> Unit) {
    enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            onResult(State.Error(t.createError()))
        }

        override fun onResponse(call: Call<T>?, response: Response<T>) {
            onResult(response.result())
        }
    })
}

