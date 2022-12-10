package com.example.datademo.data

import android.graphics.Color
import java.util.*

class Vehicle {
    var username: String = ""
    var plateNumber: String = ""
    var model: String = ""
    var color: Int = Color.parseColor("black")
    var year = 2022

    val frontView = "FRONT_VIEW.jpeg"
    val rearView = "REAR_VIEW.jpeg"
    val sideView = "SIDE_VIEW.jpeg"
    val insideView = "INSIDE_VIEW.jpeg"
    val insuranceExpiry: String = "insuranceExpiry.jpeg"
    val taxCertificateExpiry: String = "taxCertificateExpiry"
    val fitnessCertificateExpiry: String = "fitnessCertificateExpiry.jpeg"
}