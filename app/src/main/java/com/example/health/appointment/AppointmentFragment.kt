package com.example.health.appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.appointment.adapter.AppointmentListAdapter
import com.example.health.viewmodel.HealthTrackingViewModel


class AppointmentFragment : Fragment() {

    private lateinit var adapter: AppointmentListAdapter
    private lateinit var healthTrackingViewModel: HealthTrackingViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_appointment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar3).apply {
            visibility = View.VISIBLE
        }
        adapter = AppointmentListAdapter(requireActivity())
        healthTrackingViewModel = ViewModelProvider(this)
            .get(HealthTrackingViewModel::class.java)

        healthTrackingViewModel.getAllDaysData().observe(requireActivity(), {

            adapter.setList(it)
            adapter.notifyDataSetChanged()
            progressBar.visibility = View.INVISIBLE

        })

        adapter.setOnClickListener {
            val bundle = Bundle().apply {
                putString("DayName", it)
            }
            findNavController().navigate(
                R.id.action_appointmentFragment_to_appointmentAvailableTime,
                bundle
            )
        }


        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_appointment)
        val linearLayoutManager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.HORIZONTAL, false
        )
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

    }


}