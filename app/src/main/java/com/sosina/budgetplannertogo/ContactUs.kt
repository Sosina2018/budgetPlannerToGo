package com.sosina.budgetplannertogo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText

class ContactUs: AppCompatActivity() {
    private val TAG = "Sosina Haile 147951180"
    var name: EditText? = null
    var phone: String? = null
    private val MY_PERMISSIONS_REQUEST_CALL_PHONE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contactus)
    Log.i(TAG,"Contact us")
    }

    fun makePhoneCall(view: View) {

        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                MY_PERMISSIONS_REQUEST_CALL_PHONE
            )
        } else {
            callPhone()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CALL_PHONE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone()
                }
            }
        }
    }

    private fun callPhone() {
        val editText = findViewById(R.id.telephone_numberID) as EditText
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:" + editText.text.toString())
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startActivity(intent)
        }
    }
    fun getLocation(view: View){
        var intent = Intent(this,MapsActivity::class.java)
        startActivity(intent)
    }
}