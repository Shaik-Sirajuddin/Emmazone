package com.live.emmazone.utils.helper

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    private const val DATE_FORMAT = "dd-MMM-yyyy, hh:mm a"

    fun getDate(dayOfMonth: Int, monthOfYear: Int, year: Int): Date? {
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = (monthOfYear + 1)
        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        return calendar.time
    }

    fun getDate(hour: Int, minute: Int): Date? {
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = minute
        calendar[Calendar.SECOND] = 0
        return calendar.time
    }

    fun combineDateTime(date: Date, time: Date): Date? {
        val cal1 = Calendar.getInstance()
        cal1.time = date

        val cal2 = Calendar.getInstance()
        cal2.time = time

        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = cal1[Calendar.YEAR]
        calendar[Calendar.MONTH] = cal1[Calendar.MONTH]
        calendar[Calendar.DAY_OF_MONTH] = cal1[Calendar.DAY_OF_MONTH]
        calendar[Calendar.HOUR] = cal2[Calendar.HOUR_OF_DAY]
        calendar[Calendar.MINUTE] = cal2[Calendar.MINUTE]
        calendar[Calendar.SECOND] = 0
        return calendar.time
    }

    fun getFormattedDate(date: Date): String {
        val dateFormat = SimpleDateFormat(DATE_FORMAT)
        return dateFormat.format(date)
    }
}

