package com.sosina.budgetplannertogo

import android.app.LoaderManager
import android.content.ContentUris
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import kotlinx.android.synthetic.main.add_income_expense.*
//import kotlinx.android.synthetic.main.display.*
//import sun.util.locale.provider.LocaleProviderAdapter.getAdapter



class DisplayStatistics:AppCompatActivity() {
    private var studentUri: Uri? = null
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.display_statistics)
        intent =  getIntent()
        val taskUri = intent.getIntExtra("mid",0)

        println("**********${taskUri}")

        // ContentUris.parseId(taskUri)
        val TestURL = "content://com.androidatc.provider/nicknames"
        val namesuri = Uri.parse(TestURL)
        val cursor = contentResolver.query(namesuri, null, null, null,null)
        cursor.moveToPosition(taskUri)
        val rent = cursor.getDouble(cursor.getColumnIndex(BudgetContentProvider.HOUSING))
        val grocery = cursor.getDouble(cursor.getColumnIndex(BudgetContentProvider.GROCERIES))
        val personal = cursor.getDouble(cursor.getColumnIndex(BudgetContentProvider.PERSONAL))
        val insurance = cursor.getDouble(cursor.getColumnIndex(BudgetContentProvider.INSURANCE))
        val transport = cursor.getDouble(cursor.getColumnIndex(BudgetContentProvider.TRANSPORT))
        val children = cursor.getDouble(cursor.getColumnIndex(BudgetContentProvider.CHILDREN))


        var titleTextView = findViewById(R.id.name_id) as TextView
        var descrTextView = findViewById(R.id.nickname_id) as TextView

      //  titleTextView.text = taskTitle
      //  descrTextView.text = taskDescr

      /*  val extras = intent.extras

        // Check from the saved Instance
         if (bundle == null) studentUri = null
             else studentUri =  bundle.getParcelable(CustomContentProvider.CONTENT_ITEM_TYPE) as Uri

        // Or passed from the other activity
        if (extras != null) {
            studentUri = extras.getParcelable(CustomContentProvider.CONTENT_ITEM_TYPE)
            fillData(studentUri!!)*/

        // Check from the saved Instance
        //  if (bundle == null) studentUri = null
        //      else {studentUri = bundle.getParcelable(CustomContentProvider.CONTENT_ITEM_TYPE)}

        // Or passed from the other activity

    }

      /*  fun fillData(uri: Uri) {
         //   val projection = arrayOf<String>(CustomContentProvider.NAME, CustomContentProvider.NICK_NAME)
            val cursor = contentResolver.query(uri, null, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()


                name.setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomContentProvider.NAME)))
                nickname.setText(cursor.getString(cursor.getColumnIndexOrThrow(CustomContentProvider.NICK_NAME)))
                println("************************************")
                print(name)
                print(nickname)
                println("************************************")
                // Always close the cursor
                cursor.close()
            }
        }*/

    }

