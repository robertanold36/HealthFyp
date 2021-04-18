package com.example.health.appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.appointment.adapter.SubAppointmentListTimeAdapter
import com.example.health.viewmodel.HealthTrackingViewModel


class AppointmentAvailableTime : Fragment() {

    private lateinit var adapter: SubAppointmentListTimeAdapter
    private lateinit var healthTrackingViewModel: HealthTrackingViewModel
    private val args: AppointmentAvailableTimeArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appointment_available_time, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar4).apply {
            visibility = View.VISIBLE
        }

        val dayName = args.DayName

        view.findViewById<TextView>(R.id.dayName).text = dayName

        adapter = SubAppointmentListTimeAdapter(requireActivity())
        healthTrackingViewModel = ViewModelProvider(this)
            .get(HealthTrackingViewModel::class.java)

        healthTrackingViewModel.getAllAvailableAppointmentTime(dayName).observe(requireActivity(), {

            adapter.setList(it.distinct() as MutableList<String>)
            adapter.notifyDataSetChanged()
            progressBar.visibility = View.INVISIBLE
        })


        val recyclerView = view.findViewById<RecyclerView>(R.id.rvAvailableTime)
        recyclerView.adapter = adapter

    }

}