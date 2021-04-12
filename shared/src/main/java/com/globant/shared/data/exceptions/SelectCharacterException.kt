package com.globant.shared.data.exceptions

class SelectCharacterException(val code: Int = 0, override val message: String) : Exception(message)