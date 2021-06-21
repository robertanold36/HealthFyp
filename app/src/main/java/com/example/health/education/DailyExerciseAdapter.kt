package com.example.health.education

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.model.Education


class DailyExerciseAdapter(var context: Context) :
    RecyclerView.Adapter<DailyExerciseAdapter.EducationViewHolder>() {


    var dietModel1 = Education(
        R.drawable.fish,
        R.color.teal_200,
        "Food Diet",
        R.string.dietEdu, 1
    )
    var dietModel2 =
        Education(R.drawable.graph, R.color.teal_700, "Daily analysis", R.string.analysis, 2)
    var dietModel3 =
        Education(R.drawable.awareness, R.color.main_purple, "Awareness", R.string.awarenessEdu, 3)

    private var dataSet = mutableListOf(
        dietModel1,
        dietModel2,
        dietModel3
    )

    var onItemClickListener:((Int)->Unit)?=null


    inner class EducationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(education: Education) {
            itemView.findViewById<ConstraintLayout>(R.id.cardConstraints)
                .background=context.resources.getDrawable(education.color)
            itemView.findViewById<ImageView>(R.id.cardImg).setImageResource(education.image)
            itemView.findViewById<TextView>(R.id.titleEdu).text = education.title
            itemView.findViewById<TextView>(R.id.descEdu).text =
                context.resources.getText(education.desc)
            itemView.setOnClickListener {
                onItemClickListener?.invoke(education.key)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EducationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_education, parent, false)
        return EducationViewHolder(view)
    }

    override fun onBindViewHolder(holder: EducationViewHolder, position: Int) {
        val data = dataSet[position]
        holder.bindView(data)
    }

    override fun getItemCount(): Int {
        return if (dataSet.size > 0) {
            dataSet.size
        } else {
            0
        }
    }
}