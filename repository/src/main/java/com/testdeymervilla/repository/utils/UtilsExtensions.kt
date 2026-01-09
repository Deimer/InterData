package com.testdeymervilla.repository.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

private const val TAG_DATE_FORMAT_IN = "yyyy-MM-dd'T'HH:mm:ss.SSSX"
private const val TAG_DATE_FORMAT_OUT = "EEEE d, MMMM, yyyy - HH:mm"
private const val TAG_DATE_UNKNOWN = "Unknown"
private const val LANG_ES = "es"
private const val COUNTRY_CO = "CO"
private const val LOCALE_TAG_CO = "$LANG_ES-$COUNTRY_CO"

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

fun Float?.toCopFormat(): String {
    val amount = this ?: 0f
    val locale = Locale.forLanguageTag(LOCALE_TAG_CO)
    val format = NumberFormat.getCurrencyInstance(locale).apply {
        minimumFractionDigits = 0
        maximumFractionDigits = 0
    }
    return format.format(amount)
}

fun String?.firstName(): String {
    if (this.isNullOrBlank()) return ""
    val normalized = trim().replace(Regex("\\s+"), " ")
    return normalized.substringBefore(" ")
}