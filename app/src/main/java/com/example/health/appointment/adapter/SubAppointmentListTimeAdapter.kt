package com.example.health.appointment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.model.Appointment
import com.example.health.util.UtilityClass.Companion.booked
import com.google.android.material.chip.Chip

class SubAppointmentListTimeAdapter(var context: Context) :
    RecyclerView.Adapter<SubAppointmentListTimeAdapter.AppointmentListTimeViewHolder>() {

    private var dataList = listOf<Appointment>()
    fun setList(data: List<Appointment>) {
        dataList = data

    }

    var onItemClickListener: ((Appointment) -> Unit)? = null

    inner class AppointmentListTimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(appointment: Appointment) {
            itemView.findViewById<TextView>(R.id.app_time).text = appointment.time
            itemView.findViewById<Chip>(R.id.request_appointment).setOnClickListener {
                onItemClickListener?.invoke(
                   appointment
                )
                (it as Chip).text = booked
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
        val appointmentDetails: Appointment = dataList[position]
        holder.bindView(appointmentDetails)

    }

    override fun getItemCount(): Int {
        return if (dataList.isNotEmpty()) {
            dataList.size
        } else {
            0
        }
    }

}