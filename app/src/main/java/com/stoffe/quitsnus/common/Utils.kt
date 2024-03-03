package com.stoffe.quitsnus.common

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {
    fun formatTime(hours: Int, minutes: Int): String {
        val formattedHours = if (hours < 10) "0$hours" else hours.toString()
        val formattedMinutes = String.format("%02d", minutes)

        return "$formattedHours:$formattedMinutes"
    }

    fun combineDateAndTime(dateString: String, timestampString: String): Date {
        // Parse date
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val date = dateFormat.parse(dateString)

        // Parse time
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val time = timeFormat.parse(timestampString)

        // Combine date and time

        return Date(date.time + time.time)
    }

    fun isValidDate(inputDate: String): Boolean {
        return try {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            dateFormat.isLenient = false
            dateFormat.parse(inputDate)
            true // Parsing succeeded, so it's a valid date
        } catch (e: ParseException) {
            false // Parsing failed, indicating that the input is not a valid date
        }
    }
}