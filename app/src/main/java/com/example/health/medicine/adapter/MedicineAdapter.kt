package com.example.health.medicine.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.model.Medicine
import com.example.health.util.UtilityClass.Companion.dayInMills
import java.util.*
import kotlin.math.roundToInt

class MedicineAdapter(var context: Context) :
    RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    private var dataList:MutableList<Medicine> = mutableListOf<Medicine>()

    fun setList(data: MutableList<Medicine>) {
        dataList = data
    }

    inner class MedicineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(medicine: Medicine) {
            val timeTaken =
                totalDaysTime(medicine.tabletsInterval, medicine.doze, medicine.tabletsQuantity)
            val remainingTablets = getTabletsRemaining(
                medicine.date.toLong(),
                medicine.tabletsInterval,
                medicine.doze,
                medicine.tabletsQuantity
            )
            val timeRemaining = totalDaysRemaining(
                medicine.date.toLong(),
                medicine.tabletsInterval,
                medicine.doze,
                medicine.tabletsQuantity
            )
            val doze="${medicine.doze} Tablets"

            itemView.findViewById<TextView>(R.id.medicine_nme).text=medicine.name
            itemView.findViewById<TextView>(R.id.dose).text=doze
            itemView.findViewById<TextView>(R.id.tablets_remaining).text=remainingTablets
            itemView.findViewById<TextView>(R.id.doseTime).text=timeTaken
            itemView.findViewById<TextView>(R.id.dose_remaining_time).text=timeRemaining
            itemView.findViewById<TextView>(R.id.time).text=medicine.dozeTime


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_medicine_list, parent, false)
        return MedicineViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicineData: Medicine = dataList[position]
        holder.bindView(medicineData)
    }

    override fun getItemCount(): Int {
        return if (dataList.size > 0) {
            dataList.size
        } else {
            0
        }
    }

    fun totalDaysTime(interval: Int, doze: Int, quantity: Int): String {
        val totalDays = (quantity.toDouble() / (interval.toDouble() * doze.toDouble())).roundToInt()
        return if ((totalDays) > 7) {
            "${(totalDays.toDouble() / 7).roundToInt()} Total Weeks"
        } else {
            "$totalDays Days Total"
        }.toString()
    }


    fun totalDaysRemaining(millsSaved: Long, doseInterval: Int, dose: Int, quantity: Int): String {
        val totalMills = (quantity / (doseInterval * dose)) * dayInMills
        val date = Date()
        val currentMills = date.time
        val millsElapsed = currentMills - millsSaved
        return if (totalMills > millsElapsed) {
            val diff = totalMills - millsElapsed
            if (diff >= dayInMills) {
                val daysRemaining: Int = (diff / dayInMills).toInt()
                if ((daysRemaining) > 7) {
                    " | ${daysRemaining / 7} Total Weeks Remaining"
                } else {
                    " | $daysRemaining Days Remaining"
                }.toString()
            } else {
                " | doze time less than a day"
            }

        } else {
            " | Doze Time is Finished"
        }
    }

    fun getTabletsRemaining(millsSaved: Long, doseInterval: Int, dose: Int, quantity: Int): String {
        val totalMills = (quantity / (doseInterval * dose)) * dayInMills
        val date = Date()
        val millsElapsed = date.time - millsSaved
        return if (millsElapsed < totalMills) {
            if (millsElapsed >= dayInMills) {
                val daysElapsed = (millsElapsed / dayInMills).toInt()
                val doseElapsed = (doseInterval * dose) * daysElapsed
                val tabletsRemaining = quantity - doseElapsed
                " | $tabletsRemaining Tablets Remaining"
            } else {
                ""
            }
        } else {
            " | No tablets remaining"
        }
    }
}