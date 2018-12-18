package com.sosina.budgetplannertogo

import android.app.PendingIntent.getActivity
import android.content.DialogInterface
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.sosina.budgetplannertogo.R.id.image_animaion
import kotlinx.android.synthetic.main.disposable_income.*
import java.util.*

class CalculateDisposableIncome: AppCompatActivity() {
    private val TAG = "Sosina Haile 147951180"
    private var mTotalIncome: EditText? = null
    private var mIncomeTaxRate: EditText? = null
    private var result: Double = 0.0
    private var frameAnimation: AnimationDrawable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.disposable_income)
        Log.i(TAG,"Disposable Income")
       mTotalIncome = findViewById(R.id.editTextTIncome) as EditText
        mIncomeTaxRate= findViewById(R.id.editTextTExpense) as EditText
      //  var totalIncome = mTotalIncome!!.text.toString().toDouble()
            //java.lang.Double.parseDouble(mTotalIncome.getText().toString())
    //    var totalEXpense = myTotalExpense!!.text.toString().toDouble()
          //  java.lang.Double.parseDouble(myTotalExpense.getText().toString())
    //    result = mTotalIncome!!.text.toString().toDouble() - myTotalExpense!!.text.toString().toDouble()

        //val imageView = findViewById(R.id.i) as ImageView

    }
    public fun getResult(view: View){
        //    var totalEXpense = myTotalExpense!!.text.toString().toDouble()
        //  java.lang.Double.parseDouble(myTotalExpense.getText().toString())
           result = mTotalIncome!!.text.toString().toDouble() - (mTotalIncome!!.text.toString().toDouble()*
                   (mIncomeTaxRate!!.text.toString().toDouble()/100))

        //val imageView = findViewById(R.id.i) as ImageView
        image_animaion.setBackgroundResource(R.drawable.frame_animation_old2)
        frameAnimation = image_animaion.background as AnimationDrawable


        //(AnimationDrawab.imageView.getBackground()
        frameAnimation!!.start()


    /*   val ft = getSupportFragmentManager().beginTransaction()
        var mydialog  = AlertDialogFragment()
        mydialog.newInstance("Hello")

        mydialog.show(ft, "dialog")*/

        val mHandler = Handler()

            //    val ft = getSupportFragmentManager().beginTransaction()
            //    val newFragment = AlertDialogFragment.newInstance(result.toString())
             //   newFragment.show(ft, "dialog")  // run AsyncTask here.

        mHandler.postDelayed(mUpdateTimeTask, 1000);



    }

    private val mUpdateTimeTask = Runnable {

        val ft = getSupportFragmentManager().beginTransaction()
        var passMessage = "Your Disposable Income is :" + result
        val newFragment = AlertDialogFragment.newInstance(passMessage)
        newFragment.show(ft, "dialog")  // run AsyncTask here.
    }
}