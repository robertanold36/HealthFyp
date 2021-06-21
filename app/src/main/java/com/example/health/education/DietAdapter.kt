package com.example.health.education

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.model.DietModel


class DietAdapter(var context: Context) : RecyclerView.Adapter<DietAdapter.DietViewHolder>() {


    var dietModel1 = DietModel(R.drawable.vegetables, "Green Vegetables", R.string.vegetable_desc)
    var dietModel2 = DietModel(R.drawable.beans, "Beans", R.string.beans)
    var dietModel3 = DietModel(R.drawable.fish, "Fatty fish", R.string.fish)
    var dietModel4 = DietModel(R.drawable.nuts, "Wall nuts", R.string.nuts)
    var dietModel5 = DietModel(R.drawable.avocado, "Avocados", R.string.avocados_desc)
    var dietModel6 = DietModel(R.drawable.eggs, "Eggs", R.string.eggs_desc)
    private var dataSet = mutableListOf(
        dietModel1,
        dietModel2,
        dietModel3,
        dietModel4,
        dietModel5,
        dietModel6
    )


    inner class DietViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(dietModel: DietModel) {
            itemView.findViewById<ImageView>(R.id.dietImage).setImageResource(dietModel.drawable)
            itemView.findViewById<TextView>(R.id.dietTitle).text = dietModel.dietTitle
            itemView.findViewById<TextView>(R.id.dietDesc).text =
                context.resources.getText(dietModel.description)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_diet_education, parent, false)
        return DietViewHolder(view)
    }

    override fun onBindViewHolder(holder: DietViewHolder, position: Int) {
        val data=dataSet[position]
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