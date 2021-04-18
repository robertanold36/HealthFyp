package com.example.health.tracker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.health.R

class ThirstTrackerFragment :Fragment(){

     private val args:ThirstTrackerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_thirst,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val state=args.state

        Log.e("Testing",state.toString())

        view?.findViewById<Button>(R.id.chipYes)?.setOnClickListener {
            val bundle=Bundle().apply {
                putInt("state",state+1)
            }
            findNavController().navigate(R.id.action_thirstTrackerFragment_to_nauseaTrackerFragment
                ,bundle)
        }
        view?.findViewById<Button>(R.id.chipNo)?.setOnClickListener {
            val bundle=Bundle().apply {
                putInt("state",state)
            }
            findNavController().navigate(R.id.action_thirstTrackerFragment_to_nauseaTrackerFragment
                ,bundle)
        }
    }
}