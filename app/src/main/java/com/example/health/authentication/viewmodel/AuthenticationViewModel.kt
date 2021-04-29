package com.example.health.authentication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.health.authentication.AuthenticationRepository
import com.example.health.model.PatientModel

class AuthenticationViewModel(app: Application) : AndroidViewModel(app) {

    val authenticationRepository by lazy {
        AuthenticationRepository()
    }

    fun getAllHospitalNames() = authenticationRepository.getAllHospitalName()

    fun createUserAccount(patientModel: PatientModel,password:String) =
        authenticationRepository.insertPatientDetails(patientModel,password)

    fun login(email:String,password: String)=authenticationRepository.signIn(email,password)
}
