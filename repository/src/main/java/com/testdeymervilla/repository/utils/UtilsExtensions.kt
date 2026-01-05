package com.testdeymervilla.repository.utils

import java.text.SimpleDateFormat
import java.util.Locale

private const val TAG_DATE_FORMAT_IN = "yyyy-MM-dd'T'HH:mm:ss.SSSX"
private const val TAG_DATE_FORMAT_OUT = "EEEE d, MMMM, yyyy - HH:mm"
private const val TAG_DATE_UNKNOWN = "Unknown"
const val TAG_SHORT_DATE_FORMAT_OUT = "MMMM, EEEE d, yyyy"

fun String?.toHumanDate(
    dateFormatOut: String = TAG_DATE_FORMAT_OUT
): String {
    return this?.let {
        val formatIn = SimpleDateFormat(TAG_DATE_FORMAT_IN, Locale.US)
        val formatOut = SimpleDateFormat(dateFormatOut, Locale.US)
        val date = formatIn.parse(this)
        date?.let {
            formatOut.format(date)
        } ?: TAG_DATE_UNKNOWN
    } ?: TAG_DATE_UNKNOWN
}

fun Int?.orZero(): Int = this ?: 0