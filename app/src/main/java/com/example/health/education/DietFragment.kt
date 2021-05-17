package com.example.health.education

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R


class DietFragment : Fragment() {
    private lateinit var rvDiet:RecyclerView
    private lateinit var adapter: DietAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter= DietAdapter(requireContext())
        rvDiet=view.findViewById(R.id.rv_diet)
        rvDiet.adapter=adapter
    }
}