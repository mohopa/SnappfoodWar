package ir.mhp.utils.extension

import java.text.SimpleDateFormat
import java.util.Date

fun String?.safe(): String {
    return this ?: ""
}

fun String.toDate(): Date? {
    val format = SimpleDateFormat("yyyy-MM-dd")
    return try {
        format.parse(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun String.extractUrlId(): Int? {
    val segments = this.split('/')
    segments.forEach {
        it.toIntOrNull()?.let {
            return it
        }
    }
    return null
}

fun String?.toInches(): String {
    return runCatching {
        val cm = this?.toDoubleOrNull() ?: return ""
        val inches = cm * 0.393701
        return inches.toString()
    }.getOrDefault("")
}

fun String?.toFeet(): String {
    return kotlin.runCatching {
        val cm = this?.toDoubleOrNull() ?: return ""
        val feet = cm / 30.48
        return feet.toString()
    }.getOrDefault("")
}

