package com.stoffe.quitsnus.common

object Utils {
    fun formatTime(hours: Int, minutes: Int): String {
        val formattedHours = if (hours < 10) "0$hours" else hours.toString()
        val formattedMinutes = String.format("%02d", minutes)

        return "$formattedHours:$formattedMinutes"
    }
}