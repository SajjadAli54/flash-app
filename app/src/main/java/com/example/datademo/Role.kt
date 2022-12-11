package com.example.datademo


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.example.datademo.data.DatabaseHandler

class Role : AppCompatActivity() {
    private lateinit var driverRB: RadioButton
    private lateinit var passengerRB: RadioButton

    lateinit var database: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role)

        driverRB = findViewById(R.id.driverRB)
        passengerRB = findViewById(R.id.passengerRB)

        database = DatabaseHandler.getSingleton(applicationContext)
    }

    fun registerUser(v: View){
        if(driverRB.isChecked){
            callRegisterForm("Driver")
        }

        else if(passengerRB.isChecked){
            callRegisterForm("Passenger")
        }
        else {
            Toast.makeText(this, "Please Select any one option.", Toast.LENGTH_LONG).show()
        }

    }

    private fun callRegisterForm(role : String){
        val intent = Intent(this, PassengerRegistration::class.java)
        intent.putExtra("role", role)
        startActivity(intent)
    }
}