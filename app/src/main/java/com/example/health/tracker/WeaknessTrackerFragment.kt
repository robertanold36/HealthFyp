package com.example.health.tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.health.R

class WeaknessTrackerFragment :Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weakness,container,false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<Button>(R.id.chipYes)?.setOnClickListener {
            findNavController().navigate(R.id.action_weaknessTrackerFragment_to_fatigueTrackerFragment)
        }
        view?.findViewById<Button>(R.id.chipNo)?.setOnClickListener {
            findNavController().navigate(R.id.action_weaknessTrackerFragment_to_fatigueTrackerFragment)
        }
    }
}