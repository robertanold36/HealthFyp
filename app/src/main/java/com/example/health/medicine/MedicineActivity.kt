package com.example.health.medicine

import android.graphics.Color
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.health.R

class MedicineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).apply {
            title = "Medicine Reminder"
            setExpandedTitleColor(Color.BLACK)
            setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        }

    }
}