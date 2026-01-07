package com.testdeymervilla.presentation.utils

fun String.capital(): String {
    return this.lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    }
}

fun Int.toNegative(): Int = if (this > 0) -this else this