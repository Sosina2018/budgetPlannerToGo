package com.sosina.budgetplannertogo

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import android.R.attr.data
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import lecho.lib.hellocharts.view.PieChartView
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import kotlin.math.roundToInt


class PieChartActivity : AppCompatActivity() {
    private val TAG = "Sosina Haile 147951180"
   var pieChart: PieChart? = null
    var pieChartView: PieChartView? = null
    var rent: Double = 0.0
    var grocery: Double = 0.0
    var personal: Double = 0.0
    var insurance: Double = 0.0
    var transport: Double = 0.0
    var children: Double = 0.0
    var month: String = ""
    var passData = ArrayList<Float>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG,"pie chart ")
    //    pieChart = findViewById<View>(R.id.piechart) as PieChart?
       //setUpPieChartData()
        setContentView(R.layout.activity_piechart)
     //  val extras = intent.extras
    //   val arrayB = extras.getFloatArray("expenses")
    //
        getData()
        sum()
        plotData()
    }

    fun sum(){

        var sum = rent + grocery + personal + insurance + transport + children
        passData.add((rent/sum).toFloat()*100)
        passData.add((grocery/sum).toFloat()*100)
        passData.add((personal/sum).toFloat()*100)
        passData.add((insurance/sum).toFloat()*100)
        passData.add((transport/sum).toFloat()*100)
        passData.add((children/sum).toFloat()*100)

    }
    private fun getData(){
        intent =  getIntent()
        val taskUri = intent.getIntExtra("mid",0)
        Toast.makeText(baseContext, "Reciving records", Toast.LENGTH_LONG).show()
        println("**********${taskUri}")

        // ContentUris.parseId(taskUri)
        val TestURL = "content://com.sosina.budgetplannertogo.BudgetContentProvider/budgets"
        val namesuri = Uri.parse(TestURL)
        val cursor = contentResolver.query(namesuri, null, null, null,null)
        cursor.moveToPosition(taskUri)
        month = cursor.getString(cursor.getColumnIndex(BudgetContentProvider.MONTH))
        rent = cursor.getDouble(cursor.getColumnIndex(BudgetContentProvider.HOUSING))
        grocery = cursor.getDouble(cursor.getColumnIndex(BudgetContentProvider.GROCERIES))
        personal = cursor.getDouble(cursor.getColumnIndex(BudgetContentProvider.PERSONAL))
        insurance = cursor.getDouble(cursor.getColumnIndex(BudgetContentProvider.INSURANCE))
        transport = cursor.getDouble(cursor.getColumnIndex(BudgetContentProvider.TRANSPORT))
        children = cursor.getDouble(cursor.getColumnIndex(BudgetContentProvider.CHILDREN))
    }
    private fun plotData(){
        pieChartView = findViewById(R.id.chart)

        var pieData = ArrayList<SliceValue>()
        pieData.add(SliceValue(passData[0], Color.BLUE).setLabel("Housing"))
        pieData.add(SliceValue(passData[1], Color.GRAY).setLabel("Groceries"))
        pieData.add(SliceValue(passData[2], Color.RED).setLabel("Personal"))
        pieData.add(SliceValue(passData[3], Color.MAGENTA).setLabel("Insurance"))
        pieData.add(SliceValue(passData[4], Color.YELLOW).setLabel("Transport"))
        pieData.add(SliceValue(passData[5], Color.GREEN).setLabel("Children"))
        /* pieData.add(SliceValue(10f, Color.BLUE).setLabel("Housing: $10"))
         pieData.add(SliceValue(20f, Color.GRAY).setLabel("Groceries: $4"))
         pieData.add(SliceValue(15f, Color.RED).setLabel("Personal: $18"))
         pieData.add(SliceValue(25f, Color.MAGENTA).setLabel("Insurance: $28"))
         pieData.add(SliceValue(13f, Color.YELLOW).setLabel("Transport: $18"))
         pieData.add(SliceValue(17f, Color.GREEN).setLabel("Children: $28"))*/
        val pieChartData = PieChartData(pieData)
        pieChartData.setHasLabels(true).setValueLabelTextSize(20)
        pieChartData.setHasCenterCircle(true).setCenterText1(month + " Budget").setCenterText1FontSize(20)
            .setCenterText1Color(Color.parseColor("#0097A7"))
        pieChartView!!.setPieChartData(pieChartData)

    }
}
