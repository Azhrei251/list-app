package com.azhapps.listapp.account

import java.text.SimpleDateFormat
import java.util.*

private val backendDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ", Locale.ENGLISH)
private val displayDateFormat = SimpleDateFormat("dd MMM yy", Locale.ENGLISH)

fun String.toDisplayDate(): String {
    return backendDateFormat.parse(this)?.let {
        displayDateFormat.format(it)
    } ?: "Unknown Date"
}