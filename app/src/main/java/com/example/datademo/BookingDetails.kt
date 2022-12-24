package com.example.datademo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class BookingDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_details)
    }
    fun checkOut(v: View){
        val intent = Intent(this, CheckoutActivity::class.java)
        startActivity(intent)
    }
    fun cancel(v: View){
        finish()
    }
}