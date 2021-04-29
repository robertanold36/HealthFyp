package com.example.health.repository

import androidx.lifecycle.MutableLiveData
import com.example.health.model.DailyTrack
import com.example.health.model.Medicine
import com.example.health.util.UtilityClass
import com.example.health.util.UtilityClass.Companion.user
import com.google.firebase.firestore.FirebaseFirestore


class HealthTrackingRepository(private val healthTrackingDatabase: HealthTrackingDatabase) {

    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private var days: MutableLiveData<List<String>> = MutableLiveData()
    private var timeData: MutableLiveData<MutableList<String>> = MutableLiveData()


    suspend fun insertDailyData(dailyTrack: DailyTrack) =
        healthTrackingDatabase.healthTrackingDao().insertDailyTrack(dailyTrack)

    fun getAllDailyData() = healthTrackingDatabase.healthTrackingDao().getAllDailyTrack()


    fun getAllDays(): MutableLiveData<List<String>> {

        val dataDays = mutableListOf<String>()

        firebaseFirestore.collection(UtilityClass.appointment)
            .document(UtilityClass.doctorId)
            .collection(UtilityClass.appointmentList)
            .whereEqualTo("status", "Available")
            .get().addOnSuccessListener {
                for (document in it) {
                    val dayName = document.getString("DayName")
                    dataDays.add(dayName!!)
                }
                days.value = dataDays.distinct()
            }
        return days
    }

    fun getAllTimeAvailable(dayName: String): MutableLiveData<MutableList<String>> {

        val timeList: MutableList<String> = mutableListOf()

        firebaseFirestore.collection(UtilityClass.appointment)
            .document(UtilityClass.doctorId)
            .collection(UtilityClass.appointmentList)
            .whereEqualTo("DayName", dayName)
            .whereEqualTo("status", "Available").get()
            .addOnSuccessListener { dataList ->
                for (data in dataList) {
                    val dataTime = data.getString("Time")
                    timeList.add(dataTime!!)
                }
                timeData.value = timeList
            }
        return timeData
    }

    fun getAllMedicine() = healthTrackingDatabase.healthTrackingDao().getAllMedicineData()

    suspend fun insertMedicine(medicine: Medicine) =
        healthTrackingDatabase.healthTrackingDao().insertMedicineData(medicine)

}
