package com.example.health.authentication

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
import com.example.health.authentication.adapter.HospitalListAdapter
import com.example.health.authentication.viewmodel.AuthenticationViewModel

class HospitalSelection : Fragment() {
    private lateinit var authenticationViewModel: AuthenticationViewModel
    private lateinit var adapter:HospitalListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hospital_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pBar = view.findViewById<ProgressBar>(R.id.progressBar5).apply {
            visibility = View.VISIBLE
        }
        adapter = HospitalListAdapter(requireActivity())
        val rvHospitalList = view.findViewById<RecyclerView>(R.id.hospital_list)

        authenticationViewModel = ViewModelProvider(this)
            .get(AuthenticationViewModel::class.java)
        authenticationViewModel.getAllHospitalNames().observe(requireActivity(), {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
            pBar.visibility = View.INVISIBLE
        })

        rvHospitalList.adapter=adapter

        adapter.onItemClickListener = {
            val bundle = Bundle().apply {
                putParcelable("doctorDetails", it)
            }
            findNavController().navigate(R.id.action_hospitalSelection_to_registerFragment, bundle)
        }

    }

}