package com.testdeymervilla.presentation.utils

fun String.capital(): String {
    return this.lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    }
}

fun Int.toNegative(): Int = if (this > 0) -this else this

fun String.compareVersion(other: String): Int {
    val v1Nodes = this.split(".")
    val v2Nodes = other.split(".")
    val maxLength = maxOf(v1Nodes.size, v2Nodes.size)
    for (i in 0 until maxLength) {
        val v1 = v1Nodes.getOrNull(i)?.toIntOrNull() ?: 0
        val v2 = v2Nodes.getOrNull(i)?.toIntOrNull() ?: 0

        if (v1 != v2) {
            return v1.compareTo(v2)
        }
    }
    return 0
}

fun String.cleanInputs(): String {
    return this.replace("\\n", "")
        .replace("\n", "")
        .replace("\r", "")
        .trim()
}