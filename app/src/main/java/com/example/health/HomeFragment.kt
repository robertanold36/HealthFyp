package com.example.health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.HorizontalScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val horizontalScrollView =
            view?.findViewById<HorizontalScrollView>(R.id.horizontalScrollView)
        horizontalScrollView?.isHorizontalScrollBarEnabled = false;


        view?.findViewById<Button>(R.id.trackBtn)?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_statisticsFragment)
        }

    }

}