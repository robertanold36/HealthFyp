package com.example.health.tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.health.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StatisticsTrackerFragment :Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_statistics,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        view?.findViewById<FloatingActionButton>(R.id.fab)?.setOnClickListener {
            findNavController().navigate(R.id.action_statisticsFragment_to_healthTrackerFragment)
        }

    }
}