package com.example.health.appointment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.model.Appointment
import com.example.health.model.AppointmentAvailableList
import com.example.health.model.AppointmentAvailableTimeList

class SubAppointmentListTimeAdapter(subItemList: AppointmentAvailableList) :
    RecyclerView.Adapter<SubAppointmentListTimeAdapter.AppointmentListTimeViewHolder>() {


    private var dataList= subItemList

    inner class AppointmentListTimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(appointmentAvailableList: AppointmentAvailableTimeList) {
            itemView.findViewById<TextView>(R.id.app_time).text=appointmentAvailableList.time
            val appointment=Appointment(dataList.dayName,appointmentAvailableList.time)
            itemView.findViewById<Button>(R.id.request_appointment).setOnClickListener {
                onItemClickListener?.let {
                    it(appointment)
                }
            }
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
        val appointmentDetails:AppointmentAvailableTimeList=dataList.timeList[position]
        holder.bindView(appointmentDetails)

    }

    override fun getItemCount(): Int {
        return if (dataList.timeList.size > 0) {
            dataList.timeList.size
        } else {
            0
        }
    }

    private var onItemClickListener: ((Appointment) -> Unit)? = null
     fun setOnClickListener(listener:(Appointment)->Unit){
         onItemClickListener=listener
     }


}