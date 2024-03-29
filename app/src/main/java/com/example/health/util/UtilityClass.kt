package com.example.health.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.example.health.model.PatientModel
import com.google.gson.Gson
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
        const val appointment="Appointment"
        const val appointmentList="Appointment List"
        const val dayInMills=86400000
        const val user="users"
        const val booked="Booked"

        fun getPrefs(context: Context):PatientModel{
            val mPrefs:SharedPreferences=context.getSharedPreferences("Details",MODE_PRIVATE)
            val gSon = Gson()
            val json = mPrefs.getString("patientDetails", "")
            val patientModel = gSon.fromJson(json, PatientModel::class.java)
            Log.e("Testing2", patientModel.toString())
            return patientModel
        }

        fun savePrefs(context: Context,data:Any){
            val mPrefs:SharedPreferences=context.getSharedPreferences("Details",MODE_PRIVATE)
            val gSon = Gson()
            val json=gSon.toJson(data)
            mPrefs.edit {
                putString("patientDetails",json)
                commit()
            }
        }
    }
}