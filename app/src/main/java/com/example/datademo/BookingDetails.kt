package com.example.datademo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class BookingDetails : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_details)
        val carN = findViewById<TextView>(R.id.book_car_name)
        carN.text = intent.getStringExtra("carname")
        val driverN = findViewById<TextView>(R.id.book_car_owner)
        driverN.text = intent.getStringExtra("drivername")
        val dateTime = findViewById<TextView>(R.id.book_dateTime)
        dateTime.text = intent.getStringExtra("dateTime")
        val location = findViewById<TextView>(R.id.book_location)
        location.text = intent.getStringExtra("location")
    }
    fun checkOut(v: View){
        val intent = Intent(this, CheckoutActivity::class.java)
        startActivity(intent)
    }
    fun cancel(v: View){
        finish()
    }
}