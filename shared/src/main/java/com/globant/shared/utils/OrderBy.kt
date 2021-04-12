package com.globant.shared.utils

import androidx.annotation.IntDef

object Order {
    const val DESCENDING = 0
    const val ASCENDING = 1

    @IntDef(
        ASCENDING,
        DESCENDING
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class OrderBy
}