package com.example.health.analysis

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.health.R
import com.example.health.model.AnalysisShare
import com.example.health.model.PatientModel
import com.example.health.util.UtilityClass.Companion.getPrefs
import com.example.health.viewmodel.HealthTrackingViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class AnalysisFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var healthTrackingViewModel: HealthTrackingViewModel
    private val firebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }
    private val uid = FirebaseAuth.getInstance().uid

    private lateinit var patientModel: PatientModel
    private lateinit var lineChart: LineChart
    private lateinit var lineList: ArrayList<Entry>
    private lateinit var lineDataSet: LineDataSet
    private lateinit var lineData: LineData
    private lateinit var xAxis: XAxis
    private lateinit var spMeals: Spinner
    private lateinit var cardSummary: CardView
    private lateinit var weeklyAvg: TextView
    private lateinit var monthlyAvg: TextView
    private lateinit var txtMealsTime: TextView
    private lateinit var imageShare: ImageView
    private lateinit var pBarSync: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_analysis, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        healthTrackingViewModel = ViewModelProvider(this).get(HealthTrackingViewModel::class.java)

        lineChart = view.findViewById(R.id.chart_graph)
        cardSummary = view.findViewById(R.id.cardSummary)
        weeklyAvg = view.findViewById(R.id.weeklyAvg)
        monthlyAvg = view.findViewById(R.id.monthlyAvg)
        txtMealsTime = view.findViewById(R.id.mealsTime)
        imageShare = view.findViewById(R.id.imgShare)
        pBarSync = view.findViewById(R.id.pBarSync)



        xAxis = lineChart.xAxis
        spMeals = view.findViewById(R.id.sp_meals)
        patientModel = getPrefs(requireActivity())

        lineList = ArrayList()

        spMeals.onItemSelectedListener = this

        imageShare.setOnClickListener {
            pBarSync.visibility = View.VISIBLE

            val reference =
                firebaseDatabase.getReference("/track/${patientModel.doctorId}/$uid/${spMeals.selectedItem}")
            val analysisShare = AnalysisShare(
                weeklyAvg.text.toString(),
                monthlyAvg.text.toString(),
                spMeals.selectedItem.toString()
            )
            reference.setValue(analysisShare).addOnSuccessListener {
                pBarSync.visibility = View.INVISIBLE
                Toast.makeText(
                    requireActivity(),
                    "Successfully share the report to doctor",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }.addOnFailureListener {
                pBarSync.visibility = View.INVISIBLE
                Toast.makeText(requireActivity(), "Fail to share Data", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text: String = parent?.getItemAtPosition(position).toString()
        lineList.clear()
        Log.e("Testing", text)
        setDataToGraph(text)
        lineChart.notifyDataSetChanged()

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented")
    }

    private fun setDataToGraph(mealsTime: String) {

        healthTrackingViewModel.getAllDeviceTrackDetails(mealsTime)
            .observe(requireActivity(), {

                if (it.isEmpty()) {
                    Toast.makeText(
                        requireActivity(),
                        "No Available data saved to database",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.e("Testing", it.toString())
                    lineChart.visibility = View.VISIBLE

                    val count = it.count()

                    if (count > 7) {
                        var index = 1
                        val reminder = count - 7

                        for (data in it.subList(reminder, count)) {
                            lineList.add(
                                Entry(
                                    (index).toFloat(),
                                    data.sugarConcentration.toFloat()
                                )
                            )
                            index++
                        }

                        lineDataSet = LineDataSet(lineList, "Sugar Concentration mmol/l")
                        lineData = LineData(lineDataSet)
                        lineChart.data = lineData
                        lineDataSet.setColors(*ColorTemplate.JOYFUL_COLORS)
                        lineDataSet.valueTextColor = Color.GREEN
                        lineDataSet.valueTextColor = 14
                        lineDataSet.setDrawFilled(true)

                    } else {
                        var index = 1
                        for (data in it) {
                            lineList.add(
                                Entry(
                                    (index).toFloat(),
                                    data.sugarConcentration.toFloat()
                                )
                            )
                            index++
                        }

                        lineDataSet = LineDataSet(lineList, "Sugar Concentration mmol/l")
                        lineData = LineData(lineDataSet)
                        lineChart.data = lineData
                        lineDataSet.setColors(*ColorTemplate.JOYFUL_COLORS)
                        lineDataSet.valueTextColor = Color.GREEN
                        lineDataSet.valueTextColor = 14
                        lineDataSet.setDrawFilled(true)

                    }
                }

                val weeklySummary: String = if (it.count() >= 7) {
                    val size = it.count()
                    val reminder = size - 7
                    var sugarConcentrationTotal = 0.0
                    for (summary in it.subList(reminder, size)) {
                        sugarConcentrationTotal += summary.sugarConcentration
                    }
                    val sugarConcentrationAvg: String =
                        String.format("%.1f", sugarConcentrationTotal / 7)

                    "$sugarConcentrationAvg mmol/l"
                } else {
                    "No Data to display"
                }

                val monthlySummary: String = if (it.count() >= 30) {
                    val size = it.count()
                    val reminder = size - 30
                    var sugarConcentrationTotal = 0.0
                    for (summary in it.subList(reminder, size)) {
                        sugarConcentrationTotal += summary.sugarConcentration
                    }
                    val sugarConcentrationAvg: String =
                        String.format("%.1f", sugarConcentrationTotal / 30)

                    "$sugarConcentrationAvg mmol/l"
                } else {
                    "No Data to display"
                }
                weeklyAvg.text = weeklySummary
                monthlyAvg.text = monthlySummary
                txtMealsTime.text = spMeals.selectedItem.toString()
                cardSummary.visibility = View.VISIBLE
            })

    }

}