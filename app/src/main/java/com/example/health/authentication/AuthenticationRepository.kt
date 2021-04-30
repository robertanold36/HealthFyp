package com.example.health.authentication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.health.model.PatientModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthenticationRepository {

    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    var authenticationListener:AuthenticationListener?=null

    private var hospitalList: MutableLiveData<List<String>> = MutableLiveData()

    fun getAllHospitalName(): MutableLiveData<List<String>> {
        val hospitalNames: MutableList<String> = mutableListOf()
        firebaseFirestore.collection("users").get().addOnSuccessListener {
            for (document in it) {
                val hospitalName: String? = document.getString("hospital")
                Log.e("Testing", hospitalName.toString())
                hospitalNames.add(hospitalName!!)

            }
            hospitalList.value = hospitalNames.distinct()
        }

        return hospitalList
    }


    fun insertPatientDetails(patientModel: PatientModel,password:String) {
        authenticationListener?.onLoading()
        firebaseAuth.createUserWithEmailAndPassword(patientModel.email, password)
            .addOnSuccessListener {
                val uid = FirebaseAuth.getInstance().uid ?: ""
                firebaseFirestore.collection("patient").document(uid).set(patientModel)
                    .addOnSuccessListener {
                        authenticationListener?.onSuccess()
                    }.addOnFailureListener {
                    authenticationListener?.onFail("Fail to save user data")
                        Log.e("Testing",it.toString())
                    }
            }.addOnFailureListener {
                authenticationListener?.onFail("Fail to register user account")
                Log.e("Testing",it.toString())
            }
    }

    fun signIn(email:String,password: String){
        authenticationListener?.onLoading()
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
            authenticationListener?.onSuccess()
        }.addOnFailureListener {
            authenticationListener?.onFail("Fail to login")
        }
    }

    fun signOut() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser
}