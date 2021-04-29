package com.example.health.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.health.HomeActivity
import com.example.health.R
import com.google.firebase.auth.FirebaseAuth

class AuthenticationActivity : AppCompatActivity() {
    private val firebaseAuth:FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Health)
        setContentView(R.layout.activity_authentication)

        if(firebaseAuth.currentUser!=null){
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
    }

}