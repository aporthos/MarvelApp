package com.globant.shared.utils.extensions

fun Int.toTotal(): String {
    if (this.toString().length == 1) {
        return "0$this"
    }
    return this.toString()
}

fun Int?.value(default: Int = 0): Int = this ?: default
