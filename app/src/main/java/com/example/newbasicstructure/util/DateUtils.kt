package com.example.newbasicstructure.util

import com.example.newbasicstructure.data.remote.model.response.DateData
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Khush Pajwani.
 * Date 3/9/2022
 */

// 2022-04-16T19:00:00Z
const val mServerDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

fun convertDateIntoMillis(date: String, inputFormat: String): Long {
    val inputDate = SimpleDateFormat(inputFormat, Locale.ENGLISH)
    val mDate = inputDate.parse(date)
    return mDate.time
}

fun getDateData(endDate: String, inputFormat: String): DateData {
    val startMillis = System.currentTimeMillis()
    val endMillis = convertDateIntoMillis(endDate, inputFormat) ?: 0
    val diffMillis = endMillis - startMillis
    return if (diffMillis >= 0) {
        val seconds = TimeUnit.MILLISECONDS.toSeconds(diffMillis)
        val day = TimeUnit.SECONDS.toDays(seconds)
        val hours = TimeUnit.SECONDS.toHours(seconds) - day * 24
        val minutes = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.SECONDS.toHours(seconds) * 60
        val second = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.SECONDS.toMinutes(seconds) * 60
        DateData(day, hours, minutes, second)
    } else {
        DateData(0, 0, 0, 0)
    }
}