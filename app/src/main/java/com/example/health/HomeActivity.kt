package com.example.health

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.health.util.UtilityClass.Companion.getPrefs
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val patientModel=getPrefs(this)

        setSupportActionBar(findViewById(R.id.toolbar))
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val homeNavHostFragment = findViewById<View>(R.id.homeNavHostFragment)
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).apply {
            title = patientModel.name
            setExpandedTitleColor(Color.BLACK)
            setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        }

        bottomNavigationView.setupWithNavController(homeNavHostFragment.findNavController())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu,menu)
        return true
    }
}