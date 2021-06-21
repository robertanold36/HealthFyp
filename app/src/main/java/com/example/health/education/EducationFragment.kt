package com.example.health.education

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.model.EducationContentModel


class EducationFragment : Fragment() {
    private lateinit var adapter: EducationAdapter
    private lateinit var rvEducation:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_education, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter= EducationAdapter(requireContext())
        rvEducation=view.findViewById(R.id.rv_awareness)
        rvEducation.adapter=adapter
        adapter.onItemClickListener={ txt: TextView, b: EducationContentModel ->
            if(!b.isChecked){
                txt.maxLines=Integer.MAX_VALUE
                b.isChecked=true
            }else{
                txt.maxLines=1
                b.isChecked=false
            }
        }
    }
}