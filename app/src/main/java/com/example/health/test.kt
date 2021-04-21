package com.example.health

import java.util.*

fun main() {

    val day = 86400000
    val date = Date()

    print(getTabletsRemaining(1618952646366,3,2,20))

}

fun totalDaysRemaining(totalMills: Long, millsSaved: Long): String {
    val date = Date()
    val currentMills = date.time
    val millsElapsed = currentMills - millsSaved
    return if (totalMills > millsElapsed) {
        val diff = totalMills - millsElapsed
        if (diff >= 86400000) {
            val daysRemaining: Int = (diff / 86400000).toInt()
            if ((daysRemaining) > 7) {
                "${daysRemaining / 7} Total Weeks Remaining"
            } else {
                "$daysRemaining Days Remaining"
            }.toString()
        } else {
            "Medicine used less than a day"
        }

    } else {
        "Doze Time is Finished"
    }
}

fun getTabletsRemaining(millsSaved: Long,doseInterval:Int,dose:Int,quantity:Int): String {
    val totalMills=(quantity / (doseInterval * dose))*86400000
    val date = Date()
    val millsElapsed = date.time - millsSaved
    return if (millsElapsed < totalMills) {
        if(millsElapsed>=86400000){
            val daysElapsed=(millsElapsed/86400000).toInt()
            val doseElapsed=(doseInterval*dose)*daysElapsed
            val tabletsRemaining=quantity-doseElapsed
            "$tabletsRemaining Tablets Remaining"
        }else{
            ""
        }
    } else {
        "No tablets remaining"
    }
}