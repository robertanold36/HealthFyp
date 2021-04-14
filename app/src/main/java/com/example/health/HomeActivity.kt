package com.example.health

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.baoyachi.stepview.VerticalStepView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.ContextUtils.getActivity

class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(findViewById(R.id.toolbar))
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val homeNavHostFragment=findViewById<View>(R.id.homeNavHostFragment)
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).apply {
            title = "Virtual Health"
            setExpandedTitleColor(Color.BLACK)
            setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        }

        bottomNavigationView.setupWithNavController(homeNavHostFragment.findNavController())
        }

}