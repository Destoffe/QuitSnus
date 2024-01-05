package com.stoffe.quitsnus.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt

object Calculations {
    fun calculateMoneySavedPerDay(packagesPerDay: Double, packageCost: Double): Double {
        // Calculate the cost per item
        return packageCost * packagesPerDay

    }

    fun calculateDateDifference(dateString: String): Long {
        val formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy")
        val specifiedDate = LocalDateTime.parse(dateString, formatter)
        val currentDate = LocalDateTime.now().plusDays(5)

        return ChronoUnit.DAYS.between(specifiedDate, currentDate)
    }

    fun calculateSnusedNotUsed(prillorPerDosa: Int, doserPerDag: Double,dayDifference: Int) : Int {
        return (prillorPerDosa * doserPerDag * dayDifference).roundToInt()
    }
}