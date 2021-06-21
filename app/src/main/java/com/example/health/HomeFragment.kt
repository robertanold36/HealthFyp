package com.example.health


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.health.education.DailyExerciseAdapter
import com.example.health.model.AppointmentRequest
import com.example.health.model.Doctor
import com.example.health.tracker.listener.HealthTrackingEventListener
import com.example.health.util.UtilityClass.Companion.appointment
import com.example.health.util.UtilityClass.Companion.appointmentList
import com.example.health.util.UtilityClass.Companion.getPrefs
import com.example.health.viewmodel.HealthTrackingViewModel
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import de.hdodenhof.circleimageview.CircleImageView


class HomeFragment : Fragment(), HealthTrackingEventListener {

    private lateinit var healthTrackingViewModel: HealthTrackingViewModel
    private lateinit var doctorDetails: Doctor
    private var pBar: ProgressBar? = null
    private var profileImage: CircleImageView? = null
    private var nameDoctor: TextView? = null
    private var doctorName: TextView? = null
    private var chatBtn: ImageButton? = null
    private var countMsg: TextView? = null
    private var cardBadge: CardView? = null
    private var appTime: TextView? = null
    private var cardAppTime: MaterialCardView? = null
    private lateinit var adapter: DailyExerciseAdapter
    private lateinit var rvEducation: RecyclerView
    private lateinit var btnDelete: ImageView
    private lateinit var appointmentRequest: AppointmentRequest
    private val firebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        healthTrackingViewModel = ViewModelProvider(this)
            .get(HealthTrackingViewModel::class.java)
        pBar = view.findViewById(R.id.progressBar6)

        healthTrackingViewModel.repository.healthTrackingEventListener = this
        val patientModel = getPrefs(requireContext())

        rvEducation = view.findViewById(R.id.rvEducation)
        adapter = DailyExerciseAdapter(requireActivity())
        profileImage = view.findViewById(R.id.profile_image)
        nameDoctor = view.findViewById(R.id.textView12)
        doctorName = view.findViewById(R.id.doctorName)
        chatBtn = view.findViewById(R.id.btnChat)
        cardBadge = view.findViewById(R.id.card_badge)
        countMsg = view.findViewById(R.id.msg_counter)
        appTime = view.findViewById(R.id.appointment_time)
        cardAppTime = view.findViewById(R.id.materialCardView)
        btnDelete = view.findViewById(R.id.btnDelete)

        rvEducation.adapter = adapter

        adapter.onItemClickListener = {
            when (it) {
                1 -> {
                    findNavController().navigate(R.id.action_homeFragment_to_dietFragment)
                }
                2 -> {
                    findNavController().navigate(R.id.action_homeFragment_to_analysisFragment)

                }
                3 -> {
                    findNavController().navigate(R.id.action_homeFragment_to_educationFragment)
                }
                else -> {
                    Toast.makeText(requireActivity(), "None clicked", Toast.LENGTH_SHORT).show()
                }
            }
        }


        Log.e("uid", FirebaseAuth.getInstance().uid.toString())

        val uid = FirebaseAuth.getInstance().uid


        healthTrackingViewModel.getDoctorDetails(getPrefs(requireContext()).doctorId)
            .observe(requireActivity(), { doctor ->
                doctorDetails = doctor
                doctorName?.text = doctorDetails.name

                healthTrackingViewModel.getCountMessage(uid!!, doctor.doctorId)
                    .observe(requireActivity(), {
                        countMsg!!.text = it.toString()
                    })

            })

        healthTrackingViewModel.checkAppointmentRequested(uid!!, patientModel.doctorId)
            .observe(requireActivity(), {
                if (it != null) {
                    appointmentRequest = it
                    val time = it.time
                    val day = it.dayName
                    appTime!!.text = getString(R.string.app_time, time, day)
                    cardAppTime!!.visibility = View.VISIBLE
                } else {
                    cardAppTime!!.visibility = View.INVISIBLE
                }
            })

        btnDelete.setOnClickListener {
            pBar?.visibility = View.VISIBLE
            firebaseFirestore.collection(appointment).document(patientModel.doctorId)
                .collection(appointmentList).document(appointmentRequest.appointmentId)
                .update("status", "Available").addOnSuccessListener {
                    firebaseFirestore.collection(appointment).document(patientModel.doctorId)
                        .collection("AppointmentRequested").document(appointmentRequest.uid)
                        .delete()
                        .addOnSuccessListener {
                            pBar?.visibility = View.INVISIBLE
                            cardAppTime!!.visibility = View.INVISIBLE
                            Toast.makeText(
                                requireActivity(),
                                "Successfully cancel the appointment",
                                Toast.LENGTH_SHORT
                            ).show()
                        }.addOnFailureListener {
                            pBar?.visibility = View.INVISIBLE
                            Toast.makeText(
                                requireActivity(),
                                "Fail to cancel the appointment? Check your internet connection",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }.addOnFailureListener {
                    Toast.makeText(
                        requireActivity(),
                        "Fail to cancel appointment? Try Again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }


        chatBtn?.setOnClickListener {
            Log.e("TestingDoctor", doctorDetails.toString())
            val bundle = Bundle().apply {
                putParcelable("doctorDetails", doctorDetails)
            }
            findNavController().navigate(R.id.action_homeFragment_to_chatActivity, bundle)
        }
    }

    override fun onSuccess() {
        pBar?.visibility = View.INVISIBLE
        profileImage?.visibility = View.VISIBLE
        chatBtn?.visibility = View.VISIBLE
        nameDoctor?.visibility = View.VISIBLE
        if (countMsg?.text != "0") {
            cardBadge?.visibility = View.VISIBLE
        }

    }

    override fun onFail(message: String) {
        pBar?.visibility = View.INVISIBLE
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onLoading() {
        pBar?.visibility = View.VISIBLE
    }

}