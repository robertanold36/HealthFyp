package com.example.health.authentication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.model.Doctor

class HospitalListAdapter(var context: Context) :
    RecyclerView.Adapter<HospitalListAdapter.HospitalListViewHolder>() {

    private var dataList = mutableListOf<Doctor>()

    fun setList(data: MutableList<Doctor>) {
        dataList = data
    }

    var onItemClickListener:((Doctor)->Unit)?=null

    inner class HospitalListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(doctor: Doctor){
            itemView.findViewById<TextView>(R.id.txt_hospital_name).text=doctor.hospital
            itemView.setOnClickListener {
                onItemClickListener?.invoke(doctor)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HospitalListAdapter.HospitalListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_hospital_list, parent, false)
        return HospitalListViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: HospitalListAdapter.HospitalListViewHolder,
        position: Int
    ) {
       val doctorDetails:Doctor=dataList[position]
        holder.bindView(doctorDetails)
    }

    override fun getItemCount(): Int {
        return if (dataList.size > 0) {
            dataList.size
        } else {
            0
        }
    }
}