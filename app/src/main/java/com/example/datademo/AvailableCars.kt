package com.example.datademo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class AvailableCars : AppCompatActivity() {
    data class CarsInfo(val carName:String, val driverName:String, val text1:String, val text2:String)
    lateinit var carsAdapter: AvailableCarsAdapter
    lateinit var availableCarsList:ArrayList<CarsInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_cars)

        availableCarsList = ArrayList()
        carsAdapter = AvailableCarsAdapter(availableCarsList)
        findViewById<RecyclerView>(R.id.availableCarsRV).adapter = carsAdapter
        setList()
        carsAdapter.notifyDataSetChanged()

    }

    private fun setList(){
        val values = arrayOf(
            CarsInfo("Suzuki", "Ahmed Bux", "text1", "text2"),
            CarsInfo("Cultus", "Zeeshan", "text1", "text2"),
            CarsInfo("Corolla", "Aslam", "text1", "text2"),
            CarsInfo("City Seventy", "Unknow Person", "text1", "text2"),
            CarsInfo("Mersertti", "Aatif", "text1", "text2"),
            CarsInfo("BMW", "Saqib Khan", "text1", "text2")
        )
        values.forEach {
            availableCarsList.add(it)
        }


    }

    class AvailableCarsAdapter(private val carsInfo: ArrayList<CarsInfo>): RecyclerView.Adapter<AvailableCarsAdapter.MyCarsInfoHolder>(){

        inner class MyCarsInfoHolder(itemView : View):RecyclerView.ViewHolder(itemView){
            val carNameTV = itemView.findViewById<TextView>(R.id.car_name)
            val driverNameTV = itemView.findViewById<TextView>(R.id.driver_name)
            val text1TV = itemView.findViewById<TextView>(R.id.text1)
            val text2TV = itemView.findViewById<TextView>(R.id.text2)
            init {
                itemView.setOnClickListener { v: View ->
                    val intent = Intent(itemView.context, BookingDetails::class.java)
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
            holder.carNameTV.text = carsInfo[position].carName
            holder.driverNameTV.text = carsInfo[position].driverName
            holder.text1TV.text = carsInfo[position].text1
            holder.text2TV.text = carsInfo[position].text2
//            holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context, R.anim.animation_xml))
        }

        override fun getItemCount(): Int {
            return carsInfo.size
        }

    }


}