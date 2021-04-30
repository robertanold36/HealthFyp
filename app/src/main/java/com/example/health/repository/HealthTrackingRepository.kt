package com.example.health.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.health.model.DailyTrack
import com.example.health.model.Doctor
import com.example.health.model.Medicine
import com.example.health.model.PatientModel
import com.example.health.tracker.listener.HealthTrackingEventListener
import com.example.health.util.UtilityClass
import com.example.health.util.UtilityClass.Companion.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class HealthTrackingRepository(private val healthTrackingDatabase: HealthTrackingDatabase) {

    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val firebaseAuth:FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private var days: MutableLiveData<List<String>> = MutableLiveData()
    private var timeData: MutableLiveData<MutableList<String>> = MutableLiveData()
    private var patientModelData: MutableLiveData<PatientModel> = MutableLiveData()
    private var doctorData: MutableLiveData<Doctor> = MutableLiveData()
    var healthTrackingEventListener:HealthTrackingEventListener?=null


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


    fun getPatientModel(): MutableLiveData<PatientModel> {
        healthTrackingEventListener?.onLoading()
        val uid=firebaseAuth.uid?:""
        firebaseFirestore.collection("patient").document(uid)
            .get().addOnSuccessListener {
                val patientModel = it.toObject(PatientModel::class.java)
                patientModelData.value = patientModel
                Log.e("Testing",patientModel.toString())
            }.addOnFailureListener {
                Log.e("Testing", it.toString())
                healthTrackingEventListener?.onFail("Fail to Access your Details")
            }
        return patientModelData
    }

    fun getDoctorDetails(hospitalName: String): MutableLiveData<Doctor> {
        var doctor=Doctor()
        healthTrackingEventListener?.onLoading()
        firebaseFirestore.collection(user).whereEqualTo("hospital", hospitalName).get()
            .addOnSuccessListener {
               if(it.isEmpty){
                   healthTrackingEventListener?.onFail("Doctor details not found")
               }
                else{
                    healthTrackingEventListener?.onSuccess()
                    for (document in it) {
                        val doctorId = document.id
                        val phoneNumber = document.getString("PhoneNumber")
                        val name = document.getString("name")
                        val professional = document.getString("professional")
                        val hospital = document.getString("hospital")
                        doctor = Doctor(name!!, hospital!!, phoneNumber!!, professional!!, doctorId)

                    }
                    doctorData.value = doctor
                }

            }.addOnFailureListener {
                Log.e("Testing", it.toString())
                healthTrackingEventListener?.onFail("Fail to access doctor details")
            }
        return doctorData
    }

}
