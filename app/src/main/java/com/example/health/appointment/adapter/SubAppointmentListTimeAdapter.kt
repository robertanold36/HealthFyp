package com.example.health.appointment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.model.Appointment

class SubAppointmentListTimeAdapter(var context:Context) :
    RecyclerView.Adapter<SubAppointmentListTimeAdapter.AppointmentListTimeViewHolder>() {

    private var dataList= mutableListOf<String>()
    fun setList(data: MutableList<String>){
        dataList=data
    }


    inner class AppointmentListTimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(time: String) {
            itemView.findViewById<TextView>(R.id.app_time).text=time
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AppointmentListTimeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_appointment_time, parent, false)
        return AppointmentListTimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentListTimeViewHolder, position: Int) {
        val appointmentDetails: String =dataList[position]
        holder.bindView(appointmentDetails)

    }

    override fun getItemCount(): Int {
        return if (dataList.size > 0) {
            dataList.size
        } else {
            0
        }
    }

     private var onItemClickListener: ((Appointment) -> Unit)? = null
     fun setOnClickListener(listener:(Appointment)->Unit){
         onItemClickListener=listener
     }
}