package com.example.health.appointment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.appointment.adapter.SubAppointmentListTimeAdapter
import com.example.health.model.AppointmentRequest
import com.example.health.tracker.listener.HealthTrackingEventListener
import com.example.health.util.UtilityClass.Companion.getPrefs
import com.example.health.viewmodel.HealthTrackingViewModel
import com.google.firebase.auth.FirebaseAuth


class AppointmentAvailableTime : Fragment(), HealthTrackingEventListener {

    private lateinit var adapter: SubAppointmentListTimeAdapter
    private lateinit var healthTrackingViewModel: HealthTrackingViewModel
    private val args: AppointmentAvailableTimeArgs by navArgs()
    private var pBar: ProgressBar? = null
    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appointment_available_time, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pBar = view.findViewById(R.id.progressBar4)

        val dayName = args.appointmentDayName

        view.findViewById<TextView>(R.id.dayName).text = dayName

        adapter = SubAppointmentListTimeAdapter(requireActivity())
        healthTrackingViewModel = ViewModelProvider(this)
            .get(HealthTrackingViewModel::class.java)
        healthTrackingViewModel.repository.healthTrackingEventListener = this

        val patientModel = getPrefs(requireContext())
        val uid = firebaseAuth.uid

        healthTrackingViewModel.getAvailableAppointmentTime(
            dayName,
            patientModel.doctorId
        ).observe(requireActivity(),{
            adapter.setList(it.distinct())
            adapter.notifyDataSetChanged()
            pBar!!.visibility=View.INVISIBLE
        })

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvAvailableTime)
        recyclerView.adapter = adapter


        adapter.onItemClickListener = {
            val appointmentRequest =
                AppointmentRequest(it.dayName, it.time, it.appointmentId, patientModel.name, uid!!)

            healthTrackingViewModel.requestAppointment(
                it,
                patientModel.doctorId,
                appointmentRequest
            )
        }
    }

    override fun onSuccess() {
        pBar?.visibility = View.INVISIBLE
        Toast.makeText(requireActivity(), "Successfully appointment added", Toast.LENGTH_SHORT)
            .show()
        findNavController().popBackStack()
    }

    override fun onFail(message: String) {
        pBar?.visibility = View.INVISIBLE
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()

    }

    override fun onLoading() {
        pBar?.visibility = View.VISIBLE
    }

}