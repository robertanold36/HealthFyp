package com.example.health.appointment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.model.Appointment


class AppointmentListAdapter(var context: Context) :
    RecyclerView.Adapter<AppointmentListAdapter.AppointmentListViewHolder>() {


    private var dataList = listOf<Appointment>()
    private var days= arrayListOf<String>()

    fun setList(data: List<Appointment>) {
        dataList = data
    }

    inner class AppointmentListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(appointmentDayName: String) {
            itemView.findViewById<TextView>(R.id.app_day).text = appointmentDayName
            itemView.setOnClickListener {
                onItemClickListener?.let {

                    val data: List<Appointment> =
                        dataList.filter { it.dayName == appointmentDayName }

                    it(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_appointment_card,
            parent, false
        )
        return AppointmentListViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentListViewHolder, position: Int) {

        val appointmentDetails: String = getAllDays(dataList)[position]
        holder.bindView(appointmentDetails)
    }

    override fun getItemCount(): Int {
        return if (getAllDays(dataList).isNotEmpty()) {
            getAllDays(dataList).size
        } else {
            0
        }
    }

    private var onItemClickListener: ((List<Appointment>) -> Unit)? = null
    fun setOnClickListener(listener: (List<Appointment>) -> Unit) {
        onItemClickListener = listener
    }

    private fun getAllDays(data:List<Appointment>): List<String> {
        for (day in dataList){
            days.add(day.dayName)
        }

        return days.distinct()
    }
}