package com.example.health.repository

import androidx.lifecycle.MutableLiveData
import com.example.health.model.*
import com.example.health.tracker.listener.HealthTrackingEventListener
import com.example.health.util.UtilityClass
import com.example.health.util.UtilityClass.Companion.appointment
import com.example.health.util.UtilityClass.Companion.user
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore


class HealthTrackingRepository(private val healthTrackingDatabase: HealthTrackingDatabase) {

    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }


    private var appointmentList: MutableLiveData<List<Appointment>> = MutableLiveData()
    private var doctorData: MutableLiveData<Doctor> = MutableLiveData()
    var healthTrackingEventListener: HealthTrackingEventListener? = null
    private var count: MutableLiveData<Int> = MutableLiveData()
    private var appointmentRequest: MutableLiveData<AppointmentRequest> = MutableLiveData()
    private var isExist: MutableLiveData<Boolean> = MutableLiveData()


    suspend fun insertDailyData(dailyTrack: DailyTrack) =
        healthTrackingDatabase.healthTrackingDao().insertDailyTrack(dailyTrack)

    fun getAllDailyData() = healthTrackingDatabase.healthTrackingDao().getAllDailyTrack()

    suspend fun insertDeviceTrackData(deviceTrackModel: DeviceTrackModel) =
        healthTrackingDatabase.healthTrackingDao().insertDailyDeviceTrack(deviceTrackModel)


    fun getAllDeviceData(mealsTime: String) =
        healthTrackingDatabase.healthTrackingDao().getAllDeviceDailyTrack(mealsTime)


    fun getAllAvailableAppointment(doctorId: String): MutableLiveData<List<Appointment>> {
        healthTrackingEventListener?.onLoading()
        val dataAppointment = mutableListOf<Appointment>()

        firebaseFirestore.collection(appointment)
            .document(doctorId)
            .collection(UtilityClass.appointmentList)
            .whereEqualTo("status", "Available")
            .get().addOnSuccessListener {
                healthTrackingEventListener?.onSuccess()
                for (document in it) {
                    val dayName = document.getString("dayName")
                    val time = document.getString("time")
                    val status = document.getString("status")
                    val appointmentId = document.id
                    val appointment = Appointment(dayName!!, time!!, status!!, appointmentId)

                    dataAppointment.add(appointment)
                }
                appointmentList.value = dataAppointment

            }.addOnFailureListener {
                healthTrackingEventListener?.onFail("Fail to retrieve data")
            }
        return appointmentList
    }

    fun getAvailableAppointmentTime(
        day: String,
        doctorId: String
    ): MutableLiveData<List<Appointment>> {
        healthTrackingEventListener?.onLoading()
        val dataAppointment = mutableListOf<Appointment>()
        firebaseFirestore.collection(appointment).document(doctorId)
            .collection(UtilityClass.appointmentList).whereEqualTo("dayName", day)
            .whereEqualTo("status", "Available")
            .get()
            .addOnSuccessListener {

                for (document in it) {
                    val dayName = document.getString("dayName")
                    val time = document.getString("time")
                    val status = document.getString("status")
                    val appointmentId = document.id
                    val appointment = Appointment(dayName!!, time!!, status!!, appointmentId)

                    dataAppointment.add(appointment)
                }
                appointmentList.value = dataAppointment

            }.addOnFailureListener {
                healthTrackingEventListener?.onFail("Fail to retrieve data")
            }
        return appointmentList
    }

    fun requestAppointment(
        appointment: Appointment,
        doctorId: String,
        appointmentRequest: AppointmentRequest
    ) {
        healthTrackingEventListener?.onLoading()

        firebaseFirestore.collection(UtilityClass.appointment)
            .document(doctorId)
            .collection(UtilityClass.appointmentList).document(appointment.appointmentId)
            .update("status", "Unavailable").addOnSuccessListener {

                firebaseFirestore.collection(UtilityClass.appointment).document(doctorId)
                    .collection("AppointmentRequested").document(appointmentRequest.uid)
                    .set(appointmentRequest)
                    .addOnSuccessListener {
                        healthTrackingEventListener?.onSuccess()
                    }.addOnFailureListener {
                        healthTrackingEventListener?.onFail("Fail to save your appointment")
                    }
            }.addOnFailureListener {
                healthTrackingEventListener?.onFail("Fail to book appointment")
            }
    }


    fun getAllMedicine() = healthTrackingDatabase.healthTrackingDao().getAllMedicineData()

    suspend fun insertMedicine(medicine: Medicine) =
        healthTrackingDatabase.healthTrackingDao().insertMedicineData(medicine)


    fun getDoctorDetails(uid: String): MutableLiveData<Doctor> {
        healthTrackingEventListener?.onLoading()
        firebaseFirestore.collection(user).document(uid).get()
            .addOnSuccessListener {
                healthTrackingEventListener?.onSuccess()
                val doctor = it.toObject(Doctor::class.java)
                doctorData.value = doctor

            }.addOnFailureListener {
                healthTrackingEventListener?.onFail("Fail to retrieve data of doctor")
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

    fun checkAppointedRequested(
        uid: String,
        doctorId: String
    ): MutableLiveData<AppointmentRequest> {

        firebaseFirestore.collection(appointment)
            .document(doctorId)
            .collection("AppointmentRequested")
            .document(uid).get().addOnSuccessListener {
                val appointmentRequestData = it.toObject(AppointmentRequest::class.java)
                appointmentRequest.value = appointmentRequestData
            }.addOnFailureListener {
                healthTrackingEventListener?.onFail("Fail to retrieve your appointment data")
            }
        return appointmentRequest
    }


    fun isDocumentExists(id: String, doctorId: String): MutableLiveData<Boolean> {
        firebaseFirestore.collection(appointment).document(doctorId)
            .collection("AppointmentRequested").document(id).get()
            .addOnSuccessListener {
                isExist.value = it.exists()
            }

        return isExist
    }


}
