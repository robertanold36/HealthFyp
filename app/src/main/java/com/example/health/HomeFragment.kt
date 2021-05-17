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
import com.example.health.model.Doctor
import com.example.health.tracker.listener.HealthTrackingEventListener
import com.example.health.util.UtilityClass.Companion.getPrefs
import com.example.health.viewmodel.HealthTrackingViewModel
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
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
    private var name: TextView? = null
    private var appTime: TextView? = null
    private var cardAppTime:MaterialCardView?=null


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

        profileImage = view.findViewById(R.id.profile_image)
        nameDoctor = view.findViewById(R.id.textView12)
        doctorName = view.findViewById(R.id.doctorName)
        chatBtn = view.findViewById(R.id.btnChat)
        cardBadge = view.findViewById(R.id.card_badge)
        countMsg = view.findViewById(R.id.msg_counter)
        name = view.findViewById(R.id.textView5)
        appTime=view.findViewById(R.id.appointment_time)
        name?.text = patientModel.name
        cardAppTime=view.findViewById(R.id.materialCardView)


        Log.e("uid", FirebaseAuth.getInstance().uid.toString())

        val uid = FirebaseAuth.getInstance().uid

        val horizontalScrollView =
            view.findViewById<HorizontalScrollView>(R.id.horizontalScrollView)
        horizontalScrollView.isHorizontalScrollBarEnabled = false


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
                if (it!=null) {
                    val time=it.time
                    val day=it.dayName
                    appTime!!.text = getString(R.string.app_time,time,day)
                    cardAppTime!!.visibility=View.VISIBLE
                }else{
                    cardAppTime!!.visibility=View.INVISIBLE
                }
            })


        view.findViewById<Button>(R.id.trackBtn).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_statisticsFragment)
        }

        view.findViewById<Button>(R.id.btnDiet)?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_dietFragment)
        }

        view.findViewById<Button>(R.id.btnExercise).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_dailyExerciseFragment)
        }

        view.findViewById<Button>(R.id.btnEducation).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_educationFragment)
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