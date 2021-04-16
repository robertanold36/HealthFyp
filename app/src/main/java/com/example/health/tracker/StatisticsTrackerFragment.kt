package com.example.health.tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.tracker.adapter.DailyTrackerAdapter
import com.example.health.tracker.viewmodel.HealthTrackingViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StatisticsTrackerFragment : Fragment() {
    private lateinit var healthTrackingViewModel: HealthTrackingViewModel
    private lateinit var adapter: DailyTrackerAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_daily_statistics, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val progressBar= view?.findViewById<ProgressBar>(R.id.progressBar2)?.apply {
            visibility=View.VISIBLE
        }
        healthTrackingViewModel = ViewModelProvider(this)
            .get(HealthTrackingViewModel::class.java)
        val rvStatistics = view?.findViewById<RecyclerView>(R.id.rvDaily)
        adapter = DailyTrackerAdapter(requireActivity())

        view?.findViewById<FloatingActionButton>(R.id.floatingActionButton)?.setOnClickListener {
            findNavController().navigate(R.id.action_statisticsFragment_to_healthTrackerFragment)
        }

        healthTrackingViewModel.getAllDailyData().observe(requireActivity(), {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
            progressBar?.visibility=View.INVISIBLE

        })

        rvStatistics?.adapter = adapter

    }
}