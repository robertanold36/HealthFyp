package com.example.health.tracker.viewmodel

import android.app.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.health.model.DailyTrack
import com.example.health.repository.HealthTrackingDatabase
import com.example.health.repository.HealthTrackingRepository
import com.example.health.tracker.listener.HealthTrackingEventListener
import kotlinx.coroutines.*

class HealthTrackingViewModel(app: Application) : AndroidViewModel(app) {

    private val healthTrackingDatabase=HealthTrackingDatabase

    private val repository by lazy {
        HealthTrackingRepository(healthTrackingDatabase.getDatabase(app))
    }
    var healthTrackingEventListener:HealthTrackingEventListener?=null
    private var viewModelJob= Job()
    private val uiScope= CoroutineScope(Dispatchers.IO+viewModelJob)

    fun insertData(description:String,date:String,state:String)=
        uiScope.launch {
            val dailyTrack=DailyTrack(description,date,state)
            repository.insertDailyData(dailyTrack)
            withContext(Dispatchers.Main){
                healthTrackingEventListener?.onSuccess()
            }
    }

    fun getAllDailyData():LiveData<MutableList<DailyTrack>>{
      return repository.getAllDailyData()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}