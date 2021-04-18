package com.example.health.tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.health.R

class HealthTrackerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tracker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onActivityCreated(savedInstanceState)

        view.findViewById<Button>(R.id.device_tracker_btn)?.setOnClickListener {
            findNavController().navigate(R.id.action_healthTrackerFragment_to_deviceTrackerFragment)
        }
        view.findViewById<Button>(R.id.normal_tracker_btn)?.setOnClickListener {
            findNavController().navigate(R.id.action_healthTrackerFragment_to_urinatingTrackerFragment)
        }
    }
}