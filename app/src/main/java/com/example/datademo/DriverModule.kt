package com.example.drivermodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.datademo.R

class DriverModule : AppCompatActivity() {
    data class Passenger(val customer:String, val address:String, val money:Double)

    lateinit var adapter: PassengerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.driver_module)

        adapter = PassengerAdapter(getData())

        val recyclerView: RecyclerView = findViewById(R.id.driverModuleRV)
        recyclerView.adapter = adapter

    }


    private fun getData():ArrayList<Passenger>{
        val randomData = ArrayList<Passenger>()
        randomData.add(Passenger("Ameer Hamza", "Larkana - Sukkur", 200.0))
        randomData.add(Passenger("Sajjad Ali", "Hyderabad - Karachi", 100.8))
        randomData.add(Passenger("Shankar Lal", "Ghotki - Larkana", 20.60))
        randomData.add(Passenger("Shoaib", "Ghotki - Karachi", 225.8))
        randomData.add(Passenger("Rehman Abdul", "Rohri - Ghotki", 180.0))
        randomData.add(Passenger("Ammar Ali", "Sukkur - Hyderabad", 150.5))
        return randomData
    }


    class PassengerAdapter(private val data:ArrayList<Passenger>):RecyclerView.Adapter<PassengerAdapter.MyHolder>(){

        inner class MyHolder(itemView:View):RecyclerView.ViewHolder(itemView){
            val customerTV: TextView = itemView.findViewById(R.id.customerId)
            val addressTV: TextView = itemView.findViewById(R.id.addressId)
            val moneyId: TextView = itemView.findViewById(R.id.moneyId)

            init {
                itemView.setOnClickListener{
                    //On Item click
                    Toast.makeText(itemView.context, data[adapterPosition].customer, Toast.LENGTH_LONG).show()
                }
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.driver_item, parent, false)
            return MyHolder(itemView)
        }

        override fun onBindViewHolder(holder: MyHolder, position: Int) {
            holder.customerTV.text = data[position].customer
            holder.addressTV.text = data[position].address
            holder.moneyId.text = "$ ${data[position].money}"
        }

        override fun getItemCount(): Int {
            return data.size
        }

    }

}