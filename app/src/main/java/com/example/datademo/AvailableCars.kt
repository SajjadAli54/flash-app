package com.example.datademo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.datademo.data.DatabaseHandler

class AvailableCars : AppCompatActivity() {
    data class CarsInfo(val carName:String, val driverName:String, val text1:String, val text2:String)
    lateinit var carsAdapter: AvailableCarsAdapter
    lateinit var availableCarsList:ArrayList<CarsInfo>
    lateinit var databaseHandler: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_cars)

        availableCarsList = ArrayList()
        databaseHandler = DatabaseHandler.getInstance(applicationContext)
        carsAdapter = AvailableCarsAdapter(databaseHandler)
        findViewById<RecyclerView>(R.id.availableCarsRV).adapter = carsAdapter
        carsAdapter.notifyDataSetChanged()

    }

    class AvailableCarsAdapter(private val databaseHandler: DatabaseHandler): RecyclerView.Adapter<AvailableCarsAdapter.MyCarsInfoHolder>(){

        val vehicles = databaseHandler.getVehicles()

        inner class MyCarsInfoHolder(itemView : View):RecyclerView.ViewHolder(itemView){
            val carNameTV = itemView.findViewById<TextView>(R.id.car_name)
            val driverNameTV = itemView.findViewById<TextView>(R.id.driver_name)
            val text1TV = itemView.findViewById<TextView>(R.id.text1)
            val text2TV = itemView.findViewById<TextView>(R.id.text2)
            val carImage = itemView.findViewById<ImageView>(R.id.car_image)
            init {
                itemView.setOnClickListener { v: View ->
                    val intent = Intent(itemView.context, BookingDetails::class.java)
                    intent.putExtra("carname", v.findViewById<TextView>(R.id.car_name).text.toString())
                    intent.putExtra("drivername", v.findViewById<TextView>(R.id.driver_name).text.toString())
                    val app = itemView.context as AppCompatActivity
                    intent.putExtra("dateTime", app.intent.getStringExtra("dateTime"))
                    intent.putExtra("location", app.intent.getStringExtra("location"))
                    itemView.context.startActivity(intent)
                }
            }
        }


        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): AvailableCarsAdapter.MyCarsInfoHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.available_cars_item, parent, false)
            return MyCarsInfoHolder(itemView)
        }

        override fun onBindViewHolder(
            holder: AvailableCarsAdapter.MyCarsInfoHolder,
            position: Int
        ) {
            holder.carNameTV.text = vehicles[position].model
            holder.driverNameTV.text = vehicles[position].username
            holder.text1TV.text = vehicles[position].source
            holder.text2TV.text = vehicles[position].destination

            val map = hashMapOf(
                vehicles[position].frontView to holder.carImage
            )
            databaseHandler.downloadVehicleImages(vehicles[position].username,map)
        }

        override fun getItemCount(): Int {
            return vehicles.size
        }
    }


}