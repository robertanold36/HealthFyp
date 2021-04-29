package com.example.health.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.health.R
import com.example.health.authentication.viewmodel.AuthenticationViewModel

class HospitalSelection : Fragment() {
    private lateinit var authenticationViewModel: AuthenticationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hospital_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pBar = view.findViewById<ProgressBar>(R.id.progressBar5).apply {
            visibility = View.VISIBLE
        }
        val lisView = view.findViewById<ListView>(R.id.hospital_list)
        val adapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_list_item_1)

        authenticationViewModel = ViewModelProvider(this)
            .get(AuthenticationViewModel::class.java)
        authenticationViewModel.getAllHospitalNames().observe(requireActivity(), {
            adapter.addAll(it)
            adapter.notifyDataSetChanged()
            pBar.visibility = View.INVISIBLE
        })

        lisView.adapter = adapter

        lisView.setOnItemClickListener { _, _, position, _ ->
            val hospitalName: String = lisView.getItemAtPosition(position).toString()
            val bundle = Bundle().apply {
                putString("hospitalName", hospitalName)
            }
            findNavController().navigate(R.id.action_hospitalSelection_to_registerFragment, bundle)
        }

    }
}