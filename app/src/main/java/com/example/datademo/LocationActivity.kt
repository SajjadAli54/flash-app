package com.example.datademo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class LocationActivity : AppCompatActivity() {

    lateinit var locationAdapter: LocationAdapter
    lateinit var locations : ArrayList<Location>
    lateinit var recyclerView: RecyclerView
    lateinit var searchView : SearchView
    data class Location(var source:String, var destination:String)
    var source:String = ""
    val cities_names = arrayOf("Chadiza", "Chama", "Chambeshi", "Chavuma", "Chembe", "Chibombo", "Chiengi", "Chilanga", "Chililabombwe", "Chilubi", "Chingola", "Chinsali", "Chinyingi", "Chipata", "Chirundu", "Chisamba", "Choma", "Gwembe", "Isoka", "Kabompo", "Kabwe", "Kafue", "Kafulwe", "Kalabo", "Kalene Hill", "Kalomo", "Kalulushi", "Kanyembo", "Kaoma", "Kapiri Mposhi", "Kasama", "Kasempa", "Kashikishi", "Kataba", "Katete", "Kawambwa", "Kazembe", "Kazungula", "Kitwe", "Livingstone", "Luangwa", "Luanshya", "Lufwanyama", "Lukulu", "Lundazi", "Lusaka", "Macha Mission", "Makeni", "Mansa", "Mazabuka", "Mbala", "Mbereshi", "Mfuwe", "Milenge", "Misisi", "Mkushi", "Mongu", "Monze", "Mpika", "Mporokoso", "Mpulungu", "Mufulira", "Mumbwa", "Muyombe", "Mwinilunga", "Nakonde", "Nchelenge", "Ndola", "Ngoma", "Nkana", "Nseluka", "Pemba", "Petauke", "Samfya", "Senanga", "Serenje", "Sesheke", "Shiwa Ngandu", "Siavonga", "Sikalongo", "Sinazongwe", "Solwezi", "Zambezi", "Zimba")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        locations = ArrayList()

        setSourceAddress()

        recyclerView = findViewById(R.id.desListRV)
        locationAdapter = LocationAdapter(locations)
        recyclerView.adapter = locationAdapter
        initLocations()
        locationAdapter.notifyDataSetChanged()

        searchView= findViewById(R.id.desLocationSV)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(text: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(searchtext: String?): Boolean {
                filterLocations(searchtext)
                return false
            }
        })
    }

    private fun setSourceAddress(){
        val sourceVal = intent.getStringExtra("source")
        Log.e("Source Val", "onCreate: $sourceVal")

        if(sourceVal==null) {
            source = cities_names[0]
            Toast.makeText(this, "if--------IF", Toast.LENGTH_LONG).show()
        }
        else {
            source = sourceVal.toString()
            Toast.makeText(this, "else -----ELSE", Toast.LENGTH_LONG).show()
        }

        findViewById<TextView>(R.id.sourceAddTV).text = source
    }

    fun filterLocations(searchText:String?){
        val filteredLocations: ArrayList<Location> = ArrayList()

        locations.forEach {
            if (it.destination.lowercase().contains(searchText!!.lowercase())) {
                filteredLocations.add(it)
            }
        }
        if (filteredLocations.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            locationAdapter.filterLocation(filteredLocations)
        }
//        locationAdapter.notifyDataSetChanged()
    }

    fun changeSourceAdd(v: View){

        //Show Panel to get Source City and then change the locations
        val intent = Intent(this, ChangeSourceAddress::class.java)
        startActivity(intent)
        locations.forEach {
            it.source = source
        }
        locationAdapter.filterLocation(locations)
        Toast.makeText(this, source, Toast.LENGTH_LONG).show()
        finish()
    }

    private fun initLocations(){
        for(item in cities_names)
            locations.add(Location(source, item))
    }

    class LocationAdapter(private var locations: ArrayList<Location>) : RecyclerView.Adapter<LocationAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.location_item, parent, false)
            return MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val  source = locations[position].source
            val destination = locations[position].destination
//            if(source.lowercase()!=destination.lowercase())
            holder.destTV.text = source + " - " + destination
        }

        override fun getItemCount(): Int {
            return locations.size
        }

        fun filterLocation(filterList: ArrayList<Location>){
            locations = filterList
            notifyDataSetChanged()
        }

        class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val destTV: TextView = itemView.findViewById(R.id.destinationRV)
        }
    }


}