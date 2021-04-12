package com.globant.shared.data.exceptions

class UpdateException(val code: Int = 0, override val message: String) : Exception(message)