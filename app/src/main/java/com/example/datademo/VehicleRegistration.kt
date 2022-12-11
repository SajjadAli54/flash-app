package com.example.datademo

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.datademo.data.DatabaseHandler
import com.example.datademo.data.Vehicle
import kotlin.math.log

class VehicleRegistration : AppCompatActivity() {

    var uriFront: Uri? = null
    var uriRear: Uri? = null
    var uriSide: Uri? = null
    var uriInside: Uri? = null
    lateinit var username: String

    lateinit var databaseHandler: DatabaseHandler

    val cities_names = arrayOf("Chadiza", "Chama", "Chambeshi", "Chavuma", "Chembe", "Chibombo", "Chiengi", "Chilanga", "Chililabombwe", "Chilubi", "Chingola", "Chinsali", "Chinyingi", "Chipata", "Chirundu", "Chisamba", "Choma", "Gwembe", "Isoka", "Kabompo", "Kabwe", "Kafue", "Kafulwe", "Kalabo", "Kalene Hill", "Kalomo", "Kalulushi", "Kanyembo", "Kaoma", "Kapiri Mposhi", "Kasama", "Kasempa", "Kashikishi", "Kataba", "Katete", "Kawambwa", "Kazembe", "Kazungula", "Kitwe", "Livingstone", "Luangwa", "Luanshya", "Lufwanyama", "Lukulu", "Lundazi", "Lusaka", "Macha Mission", "Makeni", "Mansa", "Mazabuka", "Mbala", "Mbereshi", "Mfuwe", "Milenge", "Misisi", "Mkushi", "Mongu", "Monze", "Mpika", "Mporokoso", "Mpulungu", "Mufulira", "Mumbwa", "Muyombe", "Mwinilunga", "Nakonde", "Nchelenge", "Ndola", "Ngoma", "Nkana", "Nseluka", "Pemba", "Petauke", "Samfya", "Senanga", "Serenje", "Sesheke", "Shiwa Ngandu", "Siavonga", "Sikalongo", "Sinazongwe", "Solwezi", "Zambezi", "Zimba")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_registration)
        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cities_names)

        databaseHandler = DatabaseHandler.getSingleton(this)
        username = intent.getStringExtra("username").toString()

        val src = findViewById<Spinner>(R.id.sourceSpinner)
        src.adapter = dataAdapter
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        findViewById<Spinner>(R.id.destinationSpinner).adapter = dataAdapter
    }

    fun handleRegisterVehicle(view: View){
        val vehicle = Vehicle()
        vehicle.username = username
        vehicle.plateNumber = findViewById<EditText>(R.id.veh_plateNo).text.toString()
        vehicle.color = findViewById<EditText>(R.id.veh_color).text.toString()
        vehicle.model = findViewById<EditText>(R.id.veh_model).text.toString()
        vehicle.year = findViewById<EditText>(R.id.veh_year).text.toString()

        val map = hashMapOf(
            vehicle.frontView to uriFront,
            vehicle.rearView to uriRear,
            vehicle.sideView to uriSide,
            vehicle.insideView to uriInside
        )

        if(validateVehicle(vehicle)) {
            databaseHandler.addVehicle(vehicle, map)
            logout()
        }
        else
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
    }

    private fun validateVehicle(vehicle: Vehicle): Boolean{
        return vehicle.plateNumber.trim().isNotEmpty() &&
                vehicle.year.trim().isNotEmpty() &&
                vehicle.model.trim().isNotEmpty() &&
                vehicle.color.trim().isNotEmpty() &&
                vehicle.source != vehicle.destination
                uriFront != null &&
                uriRear != null &&
                uriSide != null &&
                uriInside != null
    }

    private fun logout(){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    private fun handleChoose(id: Int){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(Intent.createChooser(intent, "Select image from here..."), id)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK &&
            data != null &&
            data.data != null){

            val uri = data.data as Uri
            when(requestCode){
                FRONT_VIEW -> uriFront = uri
                REAR_VIEW -> uriRear = uri
                SIDE_VIEW -> uriSide = uri
                INSIDE_VIEW -> uriInside = uri
            }
        }
    }

    fun handleChooseFrontView(view: View){
        handleChoose(FRONT_VIEW)
    }

    fun handleChooseRearView(view: View){
        handleChoose(REAR_VIEW)
    }

    fun handleChooseSideView(view: View){
        handleChoose(SIDE_VIEW)
    }

    fun handleChooseInsideView(view: View){
        handleChoose(INSIDE_VIEW)
    }

    val FRONT_VIEW = 1
    val REAR_VIEW = 2
    val  SIDE_VIEW = 3
    val INSIDE_VIEW = 4
}