package com.example.health.tracker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.health.R
import com.example.health.model.DeviceTrackModel
import com.example.health.util.UtilityClass.Companion.date
import com.example.health.viewmodel.HealthTrackingViewModel

class DeviceTrackerFragment : Fragment() {
    private var meals: String? = ""
    private lateinit var healthTrackingViewModel: HealthTrackingViewModel
    private var inputData: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_symptoms_device_tracker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onActivityCreated(savedInstanceState)

        healthTrackingViewModel = ViewModelProvider(this).get(HealthTrackingViewModel::class.java)
        val sugarRange = view.findViewById<EditText>(R.id.sugar_level)


        val mealsTime = view.findViewById<RadioGroup>(R.id.radioGroup)
        mealsTime.setOnCheckedChangeListener { _, checkedId ->

            when (checkedId) {
                R.id.simpleRadioButton -> {
                    meals = view.findViewById<RadioButton>(R.id.simpleRadioButton).text.toString()
                    Log.e("Testing", meals!!)
                }
                R.id.simpleRadioButton1 -> {
                    meals = view.findViewById<RadioButton>(R.id.simpleRadioButton1).text.toString()
                    Log.e("Testing", meals!!)
                }
            }
        }

        view.findViewById<Button>(R.id.btn_save)?.setOnClickListener {

            if (sugarRange.text.isBlank() || meals!!.isBlank()) {
                Toast.makeText(requireActivity(), "Please add details", Toast.LENGTH_SHORT).show()
            } else {
                inputData = sugarRange.text.toString().toDouble()
                val description =
                    if (meals == "Before Meals" && inputData!! > 3.9 && inputData!! < 5.5) {
                        "Normal Blood Sugar Level"
                    } else if (meals == "After Meals" && inputData!! > 7.7 && inputData!! < 8.5) {
                        "Normal Blood Sugar Level"
                    } else if (meals == "Before Meals" && inputData!! > 4.0 && inputData!! < 7.1) {
                        "High Blood Sugar Level"
                    } else if (meals == "After Meals" && inputData!! > 8.5) {
                        "High Blood Sugar Level"
                    } else {
                        "PreDiabetes"
                    }

                val deviceTrackModel = DeviceTrackModel(date, description, inputData!!, meals!!)
                Log.e("Testing", deviceTrackModel.toString())


                healthTrackingViewModel.insertDeviceTrackDetails(deviceTrackModel)
                findNavController().navigate(R.id.action_deviceTrackerFragment_to_statisticsFragment)
            }
        }
    }
}