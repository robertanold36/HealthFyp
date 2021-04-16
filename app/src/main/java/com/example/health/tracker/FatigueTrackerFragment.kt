package com.example.health.tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.health.R
import com.example.health.tracker.viewmodel.HealthTrackingViewModel
import com.example.health.util.UtilityClass

class FatigueTrackerFragment : Fragment() {
    private val args: FatigueTrackerFragmentArgs by navArgs()
    private lateinit var healthTrackingViewModel: HealthTrackingViewModel
    var description = ""
    var stateCondition = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fatigue, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val state = args.state
        healthTrackingViewModel = ViewModelProvider(this)
            .get(HealthTrackingViewModel::class.java)
        view?.findViewById<Button>(R.id.chipYes)?.setOnClickListener {
            if (state >= 2) {
                description = "Blood sugar level seem to be low"
                stateCondition = "LOW"
                insertData(description, UtilityClass.date, stateCondition)

            } else {
                description = "Blood sugar level seem to be normal"
                stateCondition = "NORMAL"
                insertData(description, UtilityClass.date, stateCondition)

            }
        }
        view?.findViewById<Button>(R.id.chipNo)?.setOnClickListener {

            if (state >= 2) {
                description = "Blood sugar level seem to be low"
                stateCondition = "LOW"
                insertData(description, UtilityClass.date, stateCondition)

            } else {
                description = "Blood sugar level seem to be normal"
                stateCondition = "NORMAL"
                insertData(description, UtilityClass.date, stateCondition)

            }
        }
    }

    private fun insertData(description: String, date: String, state: String) {
        healthTrackingViewModel.insertData(description, date, state)
        findNavController().navigate(R.id.action_fatigueTrackerFragment_to_statisticsFragment)
    }
}