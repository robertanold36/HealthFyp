package com.example.health.appointment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R


class AppointmentListAdapter(var context: Context) :
    RecyclerView.Adapter<AppointmentListAdapter.AppointmentListViewHolder>() {


    private var dataList= listOf<String>()

    fun setList(data: List<String>){
        dataList=data
    }


    inner class AppointmentListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(appointmentDayName: String) {
            itemView.findViewById<TextView>(R.id.app_day).text = appointmentDayName
            itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(appointmentDayName)
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
        val appointmentDetails: String = dataList[position]
        holder.bindView(appointmentDetails)
    }

    override fun getItemCount(): Int {
        return if (dataList.isNotEmpty()) {
            dataList.size
        } else {
            0
        }
    }

    private var onItemClickListener:((String)->Unit)?=null
    fun setOnClickListener(listener:(String)->Unit){
        onItemClickListener=listener
    }
}