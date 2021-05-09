package com.example.health.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.health.model.AppointmentRequest
import com.example.health.model.DailyTrack
import com.example.health.model.Medicine
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

    fun getAllMedicineData(): LiveData<MutableList<Medicine>> {
        return repository.getAllMedicine()
    }


    fun getAllDailyData(): LiveData<MutableList<DailyTrack>> {
        return repository.getAllDailyData()
    }

    fun getAllDaysData()=repository.getAllAvailableAppointment()

    fun getHospitalName() = repository.getPatientModel()

    fun getDoctorDetails(hospitalName: String) = repository.getDoctorDetails(hospitalName)

    fun getCountMessage(senderId: String, receiverId: String) =
        repository.counterMessage(senderId, receiverId)

    fun requestAppointment(appointmentRequest: AppointmentRequest){
        repository.requestAppointment(appointmentRequest)
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}