package com.stoffe.quitsnus.common

object Calculations {
    fun calculateMoneySavedPerDay(packagesPerDay: Double, packageCost: Double): Double {
        // Calculate the cost per item
        return packageCost * packagesPerDay

    }
}