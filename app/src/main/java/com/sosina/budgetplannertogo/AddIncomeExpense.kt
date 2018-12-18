package com.sosina.budgetplannertogo

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.add_income_expense.*

class AddIncomeExpense: AppCompatActivity() {
    private val TAG = "Sosina Haile 147951180"
    var housing: Double = 0.0
    var groceries: Double = 0.0
    var personal:  Double = 0.0
    var insurance: Double = 0.0
    var transport: Double = 0.0
    var children: Double = 0.0
    var passData = floatArrayOf(0F,1F,2F,3F,4F,5F)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_income_expense)
       Log.i(TAG,"Add Income and Expense")
    }
    public fun addIncomeExpense (view: View){
        var values =  ContentValues()
        if (!(month_Edt.text.toString().isEmpty())
            && (!(income_Edt.text.toString().isEmpty()))
        &&(!(housing_Edt.text.toString().isEmpty()))
        && (!(groceries_Edt.text.toString().isEmpty()))
        &&(!(personal_Edt.text.toString().isEmpty()))
        &&(!(insurance_Edt.text.toString().isEmpty()))
        &&(!(transport_Edt.text.toString().isEmpty()))
        &&(!(children_Edt.text.toString().isEmpty()))) {
            values.put(BudgetContentProvider.MONTH, month_Edt.text.toString())
            values.put(BudgetContentProvider.INCOME,income_Edt.text.toString().toDouble())
            values.put(BudgetContentProvider.HOUSING,housing_Edt.text.toString().toDouble())
            values.put(BudgetContentProvider.GROCERIES,groceries_Edt.text.toString().toDouble())
            values.put(BudgetContentProvider.PERSONAL,personal_Edt.text.toString().toDouble())
            values.put(BudgetContentProvider.INSURANCE,insurance_Edt.text.toString().toDouble())
            values.put(BudgetContentProvider.TRANSPORT,transport_Edt.text.toString().toDouble())
            values.put(BudgetContentProvider.CHILDREN,children_Edt.text.toString().toDouble())
          //  values.put(BudgetContentProvider.ENTERTAINMENT,entertainment_Edt.text.toString().toDouble())
            contentResolver.insert(BudgetContentProvider.CONTENT_URI,values)
            Toast.makeText(baseContext, "Record Inserted", Toast.LENGTH_LONG).show()
            housing =  housing_Edt.text.toString().toDouble()
            groceries =  groceries_Edt.text.toString().toDouble()
            personal = personal_Edt.text.toString().toDouble()
            insurance = insurance_Edt.text.toString().toDouble()
            transport = transport_Edt.text.toString().toDouble()
            children = children_Edt.text.toString().toDouble()
        } else {
            Toast.makeText(baseContext, "Please enter the records first", Toast.LENGTH_LONG).show()
        }
    }
    public fun showIncomeExpense(view: View){
        Toast.makeText(baseContext, "Passing records", Toast.LENGTH_LONG).show()
       // sum()
      //  var intent = Intent(this,PieChartActivity::class.java)
     //   intent.putExtra("expenses",passData)
   //     startActivity(intent)
        intent = Intent(this,BudgetList::class.java)
        startActivity(intent)
       /* val URL = "content://com.sosina.budgetplannertogo.BudgetContentProvider/budgets"
        var monthlyBudget = Uri.parse(URL)
        var c = contentResolver.query(monthlyBudget, null, null, null,
            "month")
        var result = "Content Provider Results:"
        if (!c.moveToFirst()) {
            Toast.makeText(this, result + " no content yet!", Toast.LENGTH_LONG).show()
        } else {
            do {
                result += "\n${c.getString(c.getColumnIndex(BudgetContentProvider.MONTH))} with id" +
                        " ${c.getString(c.getColumnIndex(BudgetContentProvider.ID))} has nickname: " +
                        "${c.getString(c.getColumnIndex(BudgetContentProvider.INCOME))}"
            } while (c.moveToNext())
            if (!result.isEmpty())
                Toast.makeText(this, result, Toast.LENGTH_LONG).show()
            else
                Toast.makeText(this, "No Records present", Toast.LENGTH_LONG).show()
        }*/
    }
   public fun deletAll(view: View){
        val URL = "content://com.sosina.budgetplannertogo.BudgetContentProvider/budgets"
        var monthlyBudget = Uri.parse(URL)
        var count = contentResolver.delete(monthlyBudget, null, null)
       var countNum = "$count records are deleted."
       Toast.makeText(baseContext,countNum,Toast.LENGTH_LONG).show()
   }

    fun sum(){

        var sum = housing + groceries + personal + insurance + transport + children
        passData[0] = (housing/sum).toFloat()*100
        passData[1] = (groceries/sum).toFloat()*100
        passData[2] = (personal/sum).toFloat()*100
        passData[3] = (insurance/sum).toFloat()*100
        passData [4] = (transport/sum).toFloat()*100
        passData [5] = (children/sum).toFloat()*100

    }
}