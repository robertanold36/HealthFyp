package com.example.health.education

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.model.EducationContentModel

class EducationAdapter(var context: Context) :
    RecyclerView.Adapter<EducationAdapter.EducationViewHolder>() {

    private val educationContent1 =
        EducationContentModel(context.getString(R.string.disease_education), false)
    private val educationContent2 =
        EducationContentModel(context.getString(R.string.disease_education2), false)
    private val educationContent3 =
        EducationContentModel(context.getString(R.string.symptoms), false)
    private val educationContent4 = EducationContentModel(context.getString(R.string.causes), false)
    private var isChecked = false

    private var educationContents = arrayListOf(
        educationContent1,
        educationContent2,
        educationContent3,
        educationContent4
    )

    var onItemClickListener: ((TextView, EducationContentModel) -> Unit)? = null

    inner class EducationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(content: EducationContentModel) {
            itemView.findViewById<TextView>(R.id.txt_education).text = content.content
            itemView.setOnClickListener {
                onItemClickListener?.invoke(itemView.findViewById(R.id.txt_education), content)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EducationViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_awareness, parent, false)
        return EducationViewHolder(view)
    }

    override fun onBindViewHolder(holder: EducationViewHolder, position: Int) {
        val content = educationContents[position]
        holder.bindView(content)
    }

    override fun getItemCount(): Int {
        return if (educationContents.isEmpty()) {
            0
        } else {
            educationContents.size
        }
    }
}