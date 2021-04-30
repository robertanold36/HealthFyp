package com.example.health

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.health.model.Doctor
import com.example.health.tracker.listener.HealthTrackingEventListener
import com.example.health.viewmodel.HealthTrackingViewModel
import de.hdodenhof.circleimageview.CircleImageView


class HomeFragment : Fragment(), HealthTrackingEventListener {

    private lateinit var healthTrackingViewModel: HealthTrackingViewModel
    private lateinit var doctorDetails: Doctor
    private var pBar: ProgressBar? = null
    private var profileImage:CircleImageView?=null
    private var nameDoctor:TextView?=null
    private var doctorName:TextView?=null
    private var chatBtn:ImageButton?=null


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

        healthTrackingViewModel.repository.healthTrackingEventListener=this

         profileImage = view.findViewById(R.id.profile_image)
         nameDoctor = view.findViewById(R.id.textView12)
         doctorName = view.findViewById(R.id.doctorName)
         chatBtn = view.findViewById(R.id.btnChat)


        val horizontalScrollView =
            view.findViewById<HorizontalScrollView>(R.id.horizontalScrollView)
        horizontalScrollView.isHorizontalScrollBarEnabled = false;

        healthTrackingViewModel.getHospitalName().observe(requireActivity(), { pModel ->
            healthTrackingViewModel.getDoctorDetails(pModel.hospitalName)
                .observe(requireActivity(), { doctor ->
                    Log.e("Testing",doctor.toString())
                    doctorDetails = doctor
                    doctorName?.text = doctorDetails.name
                })
        })


        view.findViewById<Button>(R.id.trackBtn).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_statisticsFragment)
        }

        view.findViewById<Button>(R.id.btnDiet)?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_medicineAddFragment)
        }

        view.findViewById<Button>(R.id.btnExercise).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_medicineFragment)
        }

        chatBtn?.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("doctorDetails", doctorDetails)
            }
            findNavController().navigate(R.id.action_homeFragment_to_chatActivity, bundle)
        }
    }

    override fun onSuccess() {
        pBar?.visibility = View.INVISIBLE
        profileImage?.visibility = View.VISIBLE
        nameDoctor?.visibility = View.VISIBLE
        chatBtn?.visibility = View.VISIBLE
    }

    override fun onFail(message: String) {
        pBar?.visibility = View.INVISIBLE
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onLoading() {
        pBar?.visibility = View.VISIBLE
    }
}