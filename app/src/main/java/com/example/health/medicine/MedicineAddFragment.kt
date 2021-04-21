package com.example.health.medicine

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.health.R
import com.example.health.model.Medicine
import com.example.health.viewmodel.HealthTrackingViewModel
import java.util.*

class MedicineAddFragment : Fragment() {

    private val timeDate: Calendar = Calendar.getInstance()
    private val hour = timeDate.get(Calendar.HOUR_OF_DAY)
    private val tMinute = timeDate.get(Calendar.MINUTE)
    private lateinit var healthTrackingViewModel: HealthTrackingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_medicine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        healthTrackingViewModel = ViewModelProvider(this)
            .get(HealthTrackingViewModel::class.java)

        val date = Date()
        val name = view.findViewById<EditText>(R.id.name)
        val tablets = view.findViewById<EditText>(R.id.tablets)
        val interval = view.findViewById<EditText>(R.id.interval)
        val doze = view.findViewById<EditText>(R.id.doze)
        val time = view.findViewById<TextView>(R.id.doze_time)
        val btnAddTime = view.findViewById<Button>(R.id.btnTime)
        val btnSave = view.findViewById<Button>(R.id.btn_save)

        btnAddTime.setOnClickListener {
            val timePickerDialog = TimePickerDialog(
                activity, AlertDialog.THEME_HOLO_LIGHT,
                { _, hourOfDay, _ ->
                    if (hourOfDay in 0..11) {
                        val dayHour = "$hourOfDay AM"
                        time.text = if (time.text.isBlank()) {
                            dayHour
                        } else {
                            "${time.text}|$dayHour"
                        }
                    } else {
                        val dayHour = "$hourOfDay PM"
                        time.text = if (time.text.isBlank()) {
                            dayHour
                        } else {
                            "${time.text}|$dayHour"
                        }
                    }

                },
                hour,
                tMinute,
                true
            )

            timePickerDialog.show()

        }

        btnSave.setOnClickListener {
            val medicineName = name.text.toString()
            val medicineTablets = tablets.text.toString()
            val medicineInterval = interval.text.toString()
            val medicineDoze = doze.text.toString()
            val insertedDate = date.time.toString()
            val medicineTime = time.text.toString()

            if (medicineName.isBlank() ||
                medicineDoze.isBlank() ||
                medicineInterval.isBlank() ||
                medicineTablets.isBlank() ||
                medicineTime.isBlank()
            ) {
                Toast.makeText(activity, "Please fil all the fields", Toast.LENGTH_LONG).show()
            } else {
                val medicine = Medicine(
                    medicineName,
                    medicineTablets.toInt(),
                    medicineInterval.toInt(),
                    medicineDoze.toInt(),
                    medicineTime,
                    insertedDate
                )

                healthTrackingViewModel.insertMedicineData(medicine)
                findNavController().navigate(R.id.action_medicineAddFragment_to_homeFragment)
            }
        }


    }
}