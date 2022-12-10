package com.example.datademo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class VehicleRegistration : AppCompatActivity() {
    var color = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_registration)
    }

    fun handleColor(view: View){

    }
}