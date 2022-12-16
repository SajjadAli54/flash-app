package com.example.datademo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.datademo.data.DatabaseHandler

class MainActivity : AppCompatActivity() {
    lateinit var databaseHandler: DatabaseHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHandler = DatabaseHandler.getSingleton(this)
    }

    fun login(v: View){
        val name = findViewById<EditText>(R.id.username).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()
        if(findViewById<RadioButton>(R.id.passLogRB).isChecked)
            loginPassenger(name, password)
        else
            loginDriver(name, password)
    }

    private fun loginPassenger(username: String, password: String){
        if(databaseHandler.existsPassenger(username, password)) {
            val intent = Intent(this, PassengerHP::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
            Toast.makeText(this, "I am registered as passenger", Toast.LENGTH_SHORT).show()
            finish()
        }
        else
            Toast.makeText(this, "I am not registered as passenger", Toast.LENGTH_SHORT).show()
    }

    private fun loginDriver(username: String, password: String){
        if(databaseHandler.existsDriver(username, password))
            Toast.makeText(this, "I am registered as Driver", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "I am not registered as Driver", Toast.LENGTH_SHORT).show()
    }

    fun register(v:View){
        val dialogBuilder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.role_account, null)
        dialogBuilder.setView(view)

        dialogBuilder.setTitle("Select Role")
        val alertDialog: AlertDialog = dialogBuilder.create()
        alertDialog.show()

        val button = view.findViewById<Button>(R.id.nextButton)

        button.setOnClickListener {
            val driverRB = view.findViewById<RadioButton>(R.id.driverRB)
            if(driverRB.isChecked)
                callRegisterForm("Driver")
            else
                callRegisterForm("Passenger")
            alertDialog.hide()
        }
    }

    private fun callRegisterForm(role : String){
        val intent = Intent(this, PassengerRegistration::class.java)
        intent.putExtra("role", role)
        startActivity(intent)
    }
}
