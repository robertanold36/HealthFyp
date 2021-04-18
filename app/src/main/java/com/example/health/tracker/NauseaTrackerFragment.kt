package com.example.health.tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.health.R

class NauseaTrackerFragment :Fragment(){
    private val args:NauseaTrackerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nausea,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onActivityCreated(savedInstanceState)

        val state=args.state

        view.findViewById<Button>(R.id.chipYes)?.setOnClickListener {
            val bundle=Bundle().apply {
                putInt("state",state+1)
            }
            findNavController().navigate(R.id.action_nauseaTrackerFragment_to_dizzinessTrackerFragment
            ,bundle)
        }
        view.findViewById<Button>(R.id.chipNo)?.setOnClickListener {
            val bundle=Bundle().apply {
                putInt("state",state)
            }
            findNavController().navigate(R.id.action_nauseaTrackerFragment_to_dizzinessTrackerFragment
                ,bundle)
        }
    }
}