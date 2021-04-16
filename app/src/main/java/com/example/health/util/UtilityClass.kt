package com.example.health.util

import java.util.*

class UtilityClass {

    companion object {
        private val timeDate: Calendar = Calendar.getInstance()
        private val tYear = timeDate.get(Calendar.YEAR).toString()
        private val tMonth = timeDate.get(Calendar.MONTH)
        private val tDay = timeDate.get(Calendar.DAY_OF_MONTH).toString()
        private val hour = timeDate.get(Calendar.HOUR_OF_DAY).toString()
        private val tMinute = timeDate.get(Calendar.MINUTE).toString()
        private val month = tMonth + 1
        val date = "$tDay/$month/$tYear $hour:$tMinute"
        const val day="DAY"
    }
}