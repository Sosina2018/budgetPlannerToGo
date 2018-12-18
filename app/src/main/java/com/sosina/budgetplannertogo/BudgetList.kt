package com.sosina.budgetplannertogo

import android.app.LoaderManager
import android.content.ContentUris
import android.content.CursorLoader
import android.content.Intent
import android.content.Loader
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Display
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CursorAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.budget_table.*
import java.lang.Long.getLong
import java.util.ArrayList


class BudgetList: AppCompatActivity(),LoaderManager.LoaderCallbacks<Cursor>{
  private final val LOG = "Sosina Budget List"
    private val ACTIVITY_CREATE = 0
    private val ACTIVITY_EDIT = 1
    private var adapter: CursorAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.budget_table)
        getData()
        budgettable?.setOnItemClickListener { parent, view, position, id ->
           val currentTask = parent.getItemAtPosition(position) as String

           val detailsIntent = Intent(this, PieChartActivity::class.java)
         //val TASK_ID_COL = currentTask?.getColumnIndex(CustomContentProvider.ID) as Int
           val idInt = id.toInt()
            println("**********${id}")
           // val taskUri = ContentUris.withAppendedId(CustomContentProvider.CONTENT_URI, _id)
           // Log.i(LOG,"${taskUri}")
            detailsIntent.putExtra("mid",idInt)
          //  detailsIntent.setData(taskUri)
            startActivity(detailsIntent)


        }
        //    studenttable.setOnItemClickListener ({ parent, view, position, id ->  })


    }

    /*private fun createTodo() {
        val i = Intent(this, ToDoDetailActivity::class.java)
        startActivityForResult(i, ACTIVITY_CREATE)
    }*/


    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        adapter?.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
      adapter?.swapCursor(null)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val projection = arrayOf<String>(BudgetContentProvider.ID,
                BudgetContentProvider.MONTH)
        return CursorLoader(this, BudgetContentProvider.CONTENT_URI, projection,
                null, null, null)
    }


    public fun getData() {
        // Show all the records sorted by friend’s name
        val URL = "content://com.sosina.budgetplannertogo.BudgetContentProvider/budgets"
        var monthlyBudget : MutableList<String> = ArrayList()
        var budgets = Uri.parse(URL)
        var c = contentResolver.query(budgets, null, null, null, null)
        var result = "Content Provider Results:"
        if (!c.moveToFirst()) {
            Toast.makeText(this, result + " no content yet!", Toast.LENGTH_LONG).show()
        } else {
            do {
                monthlyBudget.add(c.getString(c.getColumnIndex(BudgetContentProvider.MONTH)))

            } while (c.moveToNext())
            if (!result.isEmpty())
                Toast.makeText(this, result, Toast.LENGTH_LONG).show()
            else
                Toast.makeText(this, "No Records present", Toast.LENGTH_LONG).show()
        }
        val adapter = ArrayAdapter(this@BudgetList,
                android.R.layout.simple_list_item_1,monthlyBudget)
        budgettable.adapter = adapter
    }


}