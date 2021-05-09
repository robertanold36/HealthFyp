package com.example.health.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.health.model.*
import com.example.health.tracker.listener.HealthTrackingEventListener
import com.example.health.util.UtilityClass
import com.example.health.util.UtilityClass.Companion.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore


class HealthTrackingRepository(private val healthTrackingDatabase: HealthTrackingDatabase) {

    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private var appointmentList: MutableLiveData<List<Appointment>> = MutableLiveData()
    private var timeData: MutableLiveData<List<String>> = MutableLiveData()
    private var patientModelData: MutableLiveData<PatientModel> = MutableLiveData()
    private var doctorData: MutableLiveData<Doctor> = MutableLiveData()
    var healthTrackingEventListener: HealthTrackingEventListener? = null
    private var count: MutableLiveData<Int> = MutableLiveData()


    suspend fun insertDailyData(dailyTrack: DailyTrack) =
        healthTrackingDatabase.healthTrackingDao().insertDailyTrack(dailyTrack)

    fun getAllDailyData() = healthTrackingDatabase.healthTrackingDao().getAllDailyTrack()


    fun getAllAvailableAppointment(): MutableLiveData<List<Appointment>> {

        val dataAppointment = mutableListOf<Appointment>()

        firebaseFirestore.collection(UtilityClass.appointment)
            .document(UtilityClass.doctorId)
            .collection(UtilityClass.appointmentList)
            .whereEqualTo("status", "Available")
            .get().addOnSuccessListener {
                for (document in it) {
                    val dayName = document.getString("dayName")
                    val time=document.getString("time")
                    val status=document.getString("status")
                    val appointment=Appointment(dayName!!,time!!,status!!)
                    dataAppointment.add(appointment)
                }
                appointmentList.value = dataAppointment

            }
        return appointmentList
    }


    fun getAllMedicine() = healthTrackingDatabase.healthTrackingDao().getAllMedicineData()

    suspend fun insertMedicine(medicine: Medicine) =
        healthTrackingDatabase.healthTrackingDao().insertMedicineData(medicine)


    fun getPatientModel(): MutableLiveData<PatientModel> {
        healthTrackingEventListener?.onLoading()
        val uid = firebaseAuth.uid ?: ""
        firebaseFirestore.collection("patient").document(uid)
            .get().addOnSuccessListener {
                val patientModel = it.toObject(PatientModel::class.java)
                patientModelData.value = patientModel
                Log.e("Testing", patientModel.toString())
            }.addOnFailureListener {
                Log.e("Testing", it.toString())
                healthTrackingEventListener?.onFail("Fail to Access your Details")
            }
        return patientModelData
    }

    fun getDoctorDetails(hospitalName: String): MutableLiveData<Doctor> {
        var doctor = Doctor()
        healthTrackingEventListener?.onLoading()
        firebaseFirestore.collection(user).whereEqualTo("hospital", hospitalName).get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    healthTrackingEventListener?.onFail("Doctor details not found")
                } else {

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

    fun counterMessage(senderId: String, receiverId: String): MutableLiveData<Int> {
        var counter = 0
        FirebaseDatabase.getInstance().getReference("/messages/${senderId}/${receiverId}")
            .addChildEventListener(
                object : ChildEventListener {
                    override fun onChildAdded(
                        snapshot: DataSnapshot,
                        previousChildName: String?
                    ) {

                        val chat = snapshot.getValue(ChatModel::class.java)
                        healthTrackingEventListener?.onSuccess()
                        if (chat?.senderId != senderId && chat?.read == false) {
                            counter += 1
                        }
                        count.value = counter
                    }

                    override fun onChildChanged(
                        snapshot: DataSnapshot,
                        previousChildName: String?
                    ) {

                    }

                    override fun onChildRemoved(snapshot: DataSnapshot) {

                    }

                    override fun onChildMoved(
                        snapshot: DataSnapshot,
                        previousChildName: String?
                    ) {

                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        return count
    }

    fun requestAppointment(appointmentRequest: AppointmentRequest) {
        healthTrackingEventListener?.onLoading()
        firebaseFirestore.collection(UtilityClass.appointment)
            .document(UtilityClass.doctorId)
            .collection(UtilityClass.appointmentList).whereEqualTo("time", appointmentRequest.time)
            .whereEqualTo("dayName", appointmentRequest.dayName)
            .get().addOnSuccessListener {
                for (document in it) {
                    firebaseFirestore.collection(UtilityClass.appointment)
                        .document(UtilityClass.doctorId)
                        .collection(UtilityClass.appointmentList).document(document.id)
                        .update("status", "Unavailable").addOnSuccessListener {
                            healthTrackingEventListener?.onSuccess()
                        }.addOnFailureListener {
                            healthTrackingEventListener?.onFail("Fail to book appointment")
                        }
                }
            }.addOnFailureListener {
                healthTrackingEventListener?.onFail("Fail to access the appointment data")
            }
    }

}
