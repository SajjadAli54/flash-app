package com.example.datademo

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.text.format.DateFormat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.datademo.data.DatabaseHandler
import com.google.api.Billing.BillingDestination
import org.w3c.dom.Text
import java.util.*

class PassengerHP : AppCompatActivity() {
    val LOCATION_ACTIVITY_CODE = 1

    companion object{
        var location :String = ""
        var dateSetted = false
    }
    lateinit var databaseHandler:DatabaseHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_passengerhp)
        databaseHandler = DatabaseHandler.getInstance(applicationContext)
        //Toast.makeText(this,databaseHandler.getDriver("ammar").password, Toast.LENGTH_SHORT).show()
    }

    fun selectDateTime(v: View){
        TimePickerFragment().show(supportFragmentManager, "timePicker")
    }
    fun selectLocation(v:View){
        val intent = Intent(applicationContext, ChangeSourceAddress::class.java)
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivityForResult(intent, LOCATION_ACTIVITY_CODE)
    }
    fun availability(v:View){
        if(dateSetted && !location.equals("")) {
            val intent = Intent(applicationContext, AvailableCars::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val dateTime = findViewById<TextView>(R.id.dateTimeTxt)
            val location = findViewById<TextView>(R.id.locationTxt)
            intent.putExtra("dateTime", dateTime.text.toString())
            intent.putExtra("location", location.text.toString())

            applicationContext.startActivity(intent)
        }
        else{
            Toast.makeText(this, "First set date/time and location", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.passenger_menu, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.currentLocation){
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Toast.makeText(this, "Result returned", Toast.LENGTH_SHORT).show()
        if(requestCode == LOCATION_ACTIVITY_CODE && resultCode == RESULT_OK){
            val txt = findViewById<TextView>(R.id.locationTxt)
            if(location!=null)
                txt.text = location
            else
                txt.text = "not set"
        }
    }

}
class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        // Do something with the time chosen by the user
        val newFragment = DatePickerFragment()
        val txt = activity?.findViewById<TextView>(R.id.dateTimeTxt)
        if (txt != null) {
            txt.text = " "+hourOfDay+":"+minute

        }
        newFragment.show(requireActivity().supportFragmentManager, "datePicker")
    }
}
class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireContext(), this, year, month, day)

    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        // Do something with the date chosen by the user
        val txt = activity?.findViewById<TextView>(R.id.dateTimeTxt)
        if (txt != null) {
            txt.text = ""+day+"-"+month+"-"+year + txt.text.toString()
            PassengerHP.dateSetted = true;
        }
    }
}
//map
