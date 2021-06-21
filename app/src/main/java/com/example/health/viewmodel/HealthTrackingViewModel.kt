package com.example.health.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.health.model.*
import com.example.health.repository.HealthTrackingDatabase
import com.example.health.repository.HealthTrackingRepository
import com.example.health.tracker.listener.HealthTrackingEventListener
import kotlinx.coroutines.*

class HealthTrackingViewModel(app: Application) : AndroidViewModel(app) {

    private val healthTrackingDatabase = HealthTrackingDatabase


    val repository by lazy {
        HealthTrackingRepository(healthTrackingDatabase.getDatabase(app))
    }
    var healthTrackingEventListener: HealthTrackingEventListener? = null
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    fun insertData(description: String, date: String, state: String) =
        uiScope.launch {
            val dailyTrack = DailyTrack(description, date, state)
            repository.insertDailyData(dailyTrack)
            withContext(Dispatchers.Main) {
                healthTrackingEventListener?.onSuccess()
            }
        }

    fun insertMedicineData(medicine: Medicine) = uiScope.launch {
        repository.insertMedicine(medicine)
        withContext(Dispatchers.Main) {

        }
    }

    fun insertDeviceTrackDetails(deviceTrackModel: DeviceTrackModel)=uiScope.launch {
        repository.insertDeviceTrackData(deviceTrackModel)
        withContext(Dispatchers.Main){

        }
    }

    fun getAllDeviceTrackDetails(mealsTime:String):LiveData<MutableList<DeviceTrackModel>>{
        return repository.getAllDeviceData(mealsTime)
    }

    fun getAllMedicineData(): LiveData<MutableList<Medicine>> {
        return repository.getAllMedicine()
    }


    fun getAllDailyData(): LiveData<MutableList<DailyTrack>> {
        return repository.getAllDailyData()
    }

    fun getAllDaysData(doctorId: String) = repository.getAllAvailableAppointment(doctorId)

    fun getDoctorDetails(hospitalName: String) = repository.getDoctorDetails(hospitalName)

    fun getCountMessage(senderId: String, receiverId: String) =
        repository.counterMessage(senderId, receiverId)

    fun checkDocumentExist(id:String,doctorId: String)=repository.isDocumentExists(id,doctorId)

    fun requestAppointment(
        appointment: Appointment,
        doctorId: String,
        appointmentRequest: AppointmentRequest
    ) {
        repository.requestAppointment(appointment, doctorId, appointmentRequest)
    }

    fun getAvailableAppointmentTime(
        day: String,
        doctorId: String
    ) = repository.getAvailableAppointmentTime(day, doctorId)

    fun checkAppointmentRequested(uid: String, doctorId: String) =
        repository.checkAppointedRequested(uid, doctorId)


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}