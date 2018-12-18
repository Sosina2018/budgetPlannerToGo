package com.sosina.budgetplannertogo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class About: AppCompatActivity() {
    private val TAG = "Sosina Haile 147951180"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)
        Log.d(TAG,"About activity")
    }
}