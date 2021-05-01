package com.example.health

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class MyApplication : Application() {

    override fun onCreate() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        super.onCreate()
    }
}