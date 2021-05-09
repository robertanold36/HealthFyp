package com.example.health.appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.appointment.adapter.AppointmentListAdapter
import com.example.health.tracker.listener.HealthTrackingEventListener
import com.example.health.util.UtilityClass.Companion.getPrefs
import com.example.health.viewmodel.HealthTrackingViewModel


class AppointmentFragment : Fragment(), HealthTrackingEventListener {

    private lateinit var adapter: AppointmentListAdapter
    private lateinit var healthTrackingViewModel: HealthTrackingViewModel
    private lateinit var pBar:ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_appointment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         pBar = view.findViewById(R.id.progressBar3)

        adapter = AppointmentListAdapter(requireActivity())
        healthTrackingViewModel = ViewModelProvider(this)
            .get(HealthTrackingViewModel::class.java)

        healthTrackingViewModel.repository.healthTrackingEventListener=this


        val patientModel=getPrefs(requireContext())


        healthTrackingViewModel.getAllDaysData(patientModel.doctorId).observe(requireActivity(), {

            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })

        adapter.setOnClickListener {
            val bundle = Bundle().apply {
                putString("appointmentDayName", it)
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

    override fun onSuccess() {
        pBar.visibility=View.INVISIBLE

    }

    override fun onFail(message: String) {
        pBar.visibility=View.INVISIBLE
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onLoading() {
       pBar.visibility=View.VISIBLE
    }

}