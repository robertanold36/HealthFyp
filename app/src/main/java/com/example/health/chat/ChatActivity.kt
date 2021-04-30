package com.example.health.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.navArgs
import com.example.health.R

class ChatActivity : AppCompatActivity() {
    private val args:ChatActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val doctorName=args.doctorDetails.name

        supportActionBar?.title=doctorName

    }
}