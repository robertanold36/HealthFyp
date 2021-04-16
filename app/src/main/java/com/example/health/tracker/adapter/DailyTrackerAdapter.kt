package com.example.health.tracker.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.model.DailyTrack
import com.example.health.util.UtilityClass.Companion.day

class DailyTrackerAdapter(var context: Context) :
    RecyclerView.Adapter<DailyTrackerAdapter.DailyTrackerViewHolder>() {

    var dataList = mutableListOf<DailyTrack>()

    fun setList(data: MutableList<DailyTrack>) {
        dataList = data
    }

    inner class DailyTrackerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindView(dailyTrack: DailyTrack) {
            val id=dailyTrack.id.toString()
            itemView.findViewById<TextView>(R.id.text_day).text="$day $id"
            itemView.findViewById<TextView>(R.id.text_desc).text=dailyTrack.description
            itemView.findViewById<TextView>(R.id.text_date).text=dailyTrack.date


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyTrackerViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.layout_statistics,
                    parent,
                    false
                )
        return DailyTrackerViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyTrackerViewHolder, position: Int) {
        val dailyTrack:DailyTrack=dataList[position]
        holder.bindView(dailyTrack)
    }

    override fun getItemCount(): Int {
        return if (dataList.size > 0) {
            dataList.size
        } else {
            0
        }
    }
}