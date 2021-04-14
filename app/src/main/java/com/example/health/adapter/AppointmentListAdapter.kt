package com.example.health.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.model.AppointmentAvailableList
import com.example.health.model.AppointmentEmitter


class AppointmentListAdapter(var context: Context) :
    RecyclerView.Adapter<AppointmentListAdapter.AppointmentListViewHolder>() {

    private val viewPool:RecyclerView.RecycledViewPool=RecyclerView.RecycledViewPool()
     lateinit var appointmentEmitter: AppointmentEmitter


    private var dataList= mutableListOf<AppointmentAvailableList>()

    fun setList(data: MutableList<AppointmentAvailableList>){
        dataList=data
    }


    inner class AppointmentListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvSubItem: RecyclerView=itemView.findViewById(R.id.recyclerview_app_time)

        fun bindView(appointmentAvailableList: AppointmentAvailableList) {
            itemView.findViewById<TextView>(R.id.app_day).text = appointmentAvailableList.dayName
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
        val appointmentDetails: AppointmentAvailableList = dataList[position]
        holder.bindView(appointmentDetails)

        val layoutManager = LinearLayoutManager(
            holder.rvSubItem.context,
            LinearLayoutManager.VERTICAL,
            false
        )

        layoutManager.initialPrefetchItemCount=appointmentDetails.timeList.size
        val appointmentListTimeAdapter=AppointmentListTimeAdapter(appointmentDetails)

        holder.rvSubItem.layoutManager = layoutManager
        holder.rvSubItem.adapter = appointmentListTimeAdapter
        holder.rvSubItem.setRecycledViewPool(viewPool)

       appointmentListTimeAdapter.setOnClickListener {
           appointmentEmitter.onItemClicked(it)
       }
    }

    override fun getItemCount(): Int {
        return if (dataList.size > 0) {
            dataList.size
        } else {
            0
        }
    }
}