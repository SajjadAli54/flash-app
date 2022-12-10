package com.example.datademo

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import com.example.datademo.data.DatabaseHandler
import com.example.datademo.data.Driver
import com.example.datademo.data.Passenger

class PassengerRegistration : AppCompatActivity() {
    private var role: String = ""
    private lateinit var uriPath : Uri

    var uriProfile: Uri = Uri.parse("")
    var uriNrcFront: Uri = Uri.parse("")
    var uriNrcBack: Uri = Uri.parse("")
    var uriLicFront: Uri = Uri.parse("")
    var uriLicBack: Uri = Uri.parse("")

    lateinit var database: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger_registration)

        database = DatabaseHandler.getSingleton(applicationContext)

        role = intent.getStringExtra("role").toString()
        findViewById<TextView>(R.id.regTV_id).text = "$role Registration"

        if(role == "Driver"){
            findViewById<Button>(R.id.regBTN_id).text = "NEXT"
        }
    }

    fun onBtnClick(v: View){
        if(role == "Passenger"){
           //Store the data in database and return to the welcome screen.
            registerPassenger()
        }
        else{
            registerDriver()
            //Store the data in database as in driver and Move to the registration of vehicle.
            val intent = Intent(this, VehicleRegistration::class.java)
            startActivity(intent)
        }
    }

    private fun registerPassenger(){
        val passenger = Passenger()
        passenger.username = findViewById<EditText>(R.id.username_id).text.toString()
        passenger.password = findViewById<EditText>(R.id.user_password_id).text.toString()
        passenger.firstName = findViewById<EditText>(R.id.user_firstname_id).text.toString()
        passenger.lastName = findViewById<EditText>(R.id.user_lastname_id).text.toString()
        passenger.phoneNumber = findViewById<EditText>(R.id.user_phoneNumber_id).text.toString()
        passenger.emailAddress = findViewById<EditText>(R.id.user_email_id).text.toString()
        passenger.homeAddress = findViewById<EditText>(R.id.user_address_id).text.toString()
        passenger.networkType = findViewById<EditText>(R.id.user_smartphone_id).text.toString()
        passenger.nrcNumber = findViewById<EditText>(R.id.user_nrc_id).text.toString()

        val map = hashMapOf<String, Uri>(
            passenger.profile to uriProfile,
            passenger.nrcFront to uriNrcFront,
            passenger.nrcBack to uriNrcBack,
            passenger.licenseFront to uriLicFront,
            passenger.licenseBack to uriLicBack
        )
        database.addPassenger(passenger, map)
    }

    private fun registerDriver(){
        val driver = Driver()
        driver.username = findViewById<EditText>(R.id.username_id).text.toString()
        driver.password = findViewById<EditText>(R.id.user_password_id).text.toString()
        driver.firstName = findViewById<EditText>(R.id.user_firstname_id).text.toString()
        driver.lastName = findViewById<EditText>(R.id.user_lastname_id).text.toString()
        driver.phoneNumber = findViewById<EditText>(R.id.user_phoneNumber_id).text.toString()
        driver.emailAddress = findViewById<EditText>(R.id.user_email_id).text.toString()
        driver.homeAddress = findViewById<EditText>(R.id.user_address_id).text.toString()
        driver.networkType = findViewById<EditText>(R.id.user_smartphone_id).text.toString()
        driver.nrcNumber = findViewById<EditText>(R.id.user_nrc_id).text.toString()

        val map = hashMapOf<String, Uri>(
            driver.profile to uriProfile,
            driver.nrcFront to uriNrcFront,
            driver.nrcBack to uriNrcBack,
            driver.licenseFront to uriLicFront,
            driver.licenseBack to uriLicBack
        )
        database.addDriver(driver, map)
    }

    private fun handleChoose(id: Int){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(Intent.createChooser(intent, "Select image from here..."), id)
    }

    fun handleChooseProfile(view: View){
        handleChoose(1)
    }

    fun handleChooseNrcFront(view: View){
        handleChoose(2)
    }

    fun handleChooseNrcBack(view: View){
        handleChoose(3)
    }

    fun handleChooseLicFront(view: View){
        handleChoose(4)
    }

    fun handleChooseLicBack(view: View){
        handleChoose(5)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK &&
            data != null &&
            data.data != null){

            val uri = data.data as Uri
            when(requestCode){
                1 -> uriProfile = uri
                2 -> uriNrcFront = uri
                3 -> uriNrcBack = uri
                4 -> uriLicFront = uri
                5 -> uriLicBack = uri
            }
        }
    }

    private fun getImage(uri: Uri): Bitmap {
        return MediaStore.Images.Media.getBitmap(contentResolver, uriPath)
    }

}