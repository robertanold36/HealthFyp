package com.example.health.authentication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.health.authentication.AuthenticationRepository
import com.example.health.model.PatientModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AuthenticationViewModel(app: Application) : AndroidViewModel(app) {

    val authenticationRepository by lazy {
        AuthenticationRepository()
    }

    private var job= Job()
    private val uiScope= CoroutineScope(Dispatchers.Main+job)

    fun getAllHospitalNames() = authenticationRepository.getAllHospitalName()

    fun createUserAccount(patientModel: PatientModel,password:String) =uiScope.launch {
        authenticationRepository.insertPatientDetails(patientModel,password)
    }

    fun login(email:String,password: String)=authenticationRepository.signIn(email,password)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
