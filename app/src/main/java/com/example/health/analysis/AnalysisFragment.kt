package com.example.health.analysis

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import com.example.health.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate


class AnalysisFragment : Fragment() {

    private lateinit var entryList:ArrayList<Entry>
    private lateinit var lineDataSet:LineDataSet
    private lateinit var lineData: LineData
    private lateinit var lineChart:LineChart
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_analysis, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lineChart=view.findViewById(R.id.line_chart)

        entryList= ArrayList()
        entryList.add(Entry(1F, 1F))
        entryList.add(Entry(2F, 0F))
        entryList.add(Entry(3F, 0F))
        entryList.add(Entry(4F, 1F))
        entryList.add(Entry(5F, -1F))
        entryList.add(Entry(6F, 1F))

        lineDataSet= LineDataSet(entryList,"Entries")
        lineData= LineData(lineDataSet)
        lineChart.data=lineData
        lineDataSet.color=Color.BLACK
        //lineDataSet.setColors(*ColorTemplate.JOYFUL_COLORS)
        lineDataSet.valueTextColor=Color.parseColor("#faaca8")
        lineDataSet.valueTextSize=13f
        lineDataSet.setDrawFilled(true)
        lineChart.xAxis.granularity=1f
        lineChart.axisRight.granularity=1f
        lineChart.axisLeft.granularity=1f

    }

}