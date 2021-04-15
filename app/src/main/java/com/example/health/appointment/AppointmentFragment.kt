package com.example.health.appointment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.appointment.adapter.AppointmentListAdapter
import com.example.health.model.Appointment
import com.example.health.model.AppointmentAvailableList
import com.example.health.model.AppointmentAvailableTimeList
import com.example.health.model.AppointmentEmitter


class AppointmentFragment : Fragment(),AppointmentEmitter {

    private lateinit var adapter: AppointmentListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_appointment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appointmentAvailableList1 =
            AppointmentAvailableList(
                "Monday", mutableListOf(
                    AppointmentAvailableTimeList("09:00 AM"),
                    AppointmentAvailableTimeList("10:00 AM"),
                    AppointmentAvailableTimeList("11:00 AM")
                )
            )
        val appointmentAvailableList2 =
            AppointmentAvailableList(
                "Sunday", mutableListOf(
                    AppointmentAvailableTimeList("09:00 AM"),
                    AppointmentAvailableTimeList("10:00 AM"),
                    AppointmentAvailableTimeList("11:00 AM")

                )
            )
        val appointmentAvailableList3 =
            AppointmentAvailableList(
                "Tuesday", mutableListOf(
                    AppointmentAvailableTimeList("09:00 AM"),
                    AppointmentAvailableTimeList("10:00 AM")

                )
            )
        val appointmentAvailableList4 =
            AppointmentAvailableList(
                "Friday", mutableListOf(
                    AppointmentAvailableTimeList("09:00 AM"),
                    AppointmentAvailableTimeList("10:00 AM"),
                    AppointmentAvailableTimeList("11:00 AM")
                )
            )

        val appointmentAvailableList5 =
            AppointmentAvailableList(
                "Saturday", mutableListOf(
                    AppointmentAvailableTimeList("09:00 AM"),
                    AppointmentAvailableTimeList("10:00 AM"),
                    AppointmentAvailableTimeList("11:00 AM")
                )
            )

        adapter = AppointmentListAdapter(requireActivity())
        adapter.setList(
            mutableListOf(
                appointmentAvailableList1,
                appointmentAvailableList2,
                appointmentAvailableList3,
                appointmentAvailableList4,
                appointmentAvailableList5
            )
        )
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_appointment)
        val linearLayoutManager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.HORIZONTAL, false
        )
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        adapter.appointmentEmitter=this

    }

    override fun onItemClicked(appointment: Appointment) {
        Log.e("AppointmentFragment",appointment.dayName)
        Toast.makeText(requireActivity(),appointment.time,Toast.LENGTH_LONG).show()
    }

}