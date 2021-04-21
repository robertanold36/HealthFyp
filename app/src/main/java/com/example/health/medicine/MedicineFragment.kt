package com.example.health.medicine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.medicine.adapter.MedicineAdapter
import com.example.health.viewmodel.HealthTrackingViewModel

class MedicineFragment : Fragment() {
    private lateinit var adapter: MedicineAdapter
    private lateinit var healthTrackingViewModel: HealthTrackingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_medicine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MedicineAdapter(requireActivity())
        val rvMedicine=view.findViewById<RecyclerView>(R.id.rv_medicine)
        healthTrackingViewModel = ViewModelProvider(this)
            .get(HealthTrackingViewModel::class.java)
        healthTrackingViewModel.getAllMedicineData().observe(requireActivity(), {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })

        rvMedicine.adapter=adapter
    }
}