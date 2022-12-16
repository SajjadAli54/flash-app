package com.example.datademo

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.datademo.data.DatabaseHandler
import com.example.datademo.data.Driver
import com.example.datademo.data.Passenger
import com.example.datademo.data.Person

class PassengerRegistration : AppCompatActivity() {
    private var role: String = ""

    var uriProfile: Uri? = null
    var uriNrcFront: Uri? = null
    var uriNrcBack: Uri? = null
    var uriLicFront: Uri? = null
    var uriLicBack: Uri? = null

    lateinit var database: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger_registration)

        database = DatabaseHandler.getInstance(applicationContext)

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

        val map = hashMapOf<String, Uri?>(
            passenger.profile to uriProfile,
            passenger.nrcFront to uriNrcFront,
            passenger.nrcBack to uriNrcBack,
            passenger.licenseFront to uriLicFront,
            passenger.licenseBack to uriLicBack
        )
        if(validateData(passenger)) {
            database.addPassenger(passenger, map)
            logout()
        }
    }

    fun logout(){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
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

        val map = hashMapOf<String, Uri?>(
            driver.profile to uriProfile,
            driver.nrcFront to uriNrcFront,
            driver.nrcBack to uriNrcBack,
            driver.licenseFront to uriLicFront,
            driver.licenseBack to uriLicBack
        )

        if(validateData(driver)) {
            database.addDriver(driver, map)

            val intent = Intent(this, VehicleRegistration::class.java)
            intent.putExtra("username", driver.username)
            startActivity(intent)
        }
    }

    fun validateEmail(email: String): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    fun validatePhoneNumber(phone: String): Boolean{
        return android.util.Patterns.PHONE.matcher(phone).matches()
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

    fun validateData(person: Person): Boolean{
        val flag =  person.username.trim().isNotEmpty() &&
            person.password.trim().isNotEmpty() &&
                person.firstName.trim().isNotEmpty()  &&
                person.lastName.trim().isNotEmpty() &&
                person.phoneNumber.trim().isNotEmpty() &&
                person.emailAddress.trim().isNotEmpty() &&
                person.homeAddress.trim().isNotEmpty() &&
                person.networkType.trim().isNotEmpty() &&
                person.nrcNumber.trim().isNotEmpty() &&

                uriProfile != null &&
                uriLicFront != null &&
                uriLicBack != null &&
                uriNrcFront != null &&
                uriNrcBack != null
        if(!flag) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return false
        }
        if(!validateEmail(person.emailAddress)){
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
            return false
        }
        if(!validatePhoneNumber(person.phoneNumber)){
            Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}