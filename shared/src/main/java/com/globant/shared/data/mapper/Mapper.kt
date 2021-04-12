package com.globant.shared.data.mapper

abstract class Mapper<in IN, out OUT> {

    abstract fun mapFrom(from: IN): OUT

    fun transform(from: List<IN>): List<OUT> = from.map { mapFrom(it) }
}