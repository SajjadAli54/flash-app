package com.example.datademo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ChangeSourceAddress : AppCompatActivity() {
    data class SourceAddress(val source:String)
    lateinit var sourceAddressAdapter: SourceAddressAdapter
    lateinit var sourceAddList: ArrayList<SourceAddress>
    lateinit var searchView : SearchView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_source_address)
        searchView = findViewById(R.id.sourceAddSV)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                filterSourceAddress(text)
                return false
            }

        })
        sourceAddList = ArrayList()
        sourceAddressAdapter = SourceAddressAdapter(sourceAddList)
        val recyclerView = findViewById<RecyclerView>(R.id.sourceAddRV)
        recyclerView.adapter = sourceAddressAdapter
        initSourceAdd()
        sourceAddressAdapter.notifyDataSetChanged()

        //inserting event on items in recyclerview

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123){
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun filterSourceAddress(searchText: String?){
        val filteredLocations: ArrayList<SourceAddress> = ArrayList()

        sourceAddList.forEach {
            if (it.source.lowercase().contains(searchText!!.lowercase())) {
                filteredLocations.add(it)
            }
        }
        if (filteredLocations.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            sourceAddressAdapter.setFilteredList(filteredLocations)
        }
//        sourceAddressAdapter.notifyDataSetChanged()

    }

    private fun initSourceAdd(){
        val options = arrayOf("Chadiza", "Chama", "Chambeshi", "Chavuma", "Chembe", "Chibombo", "Chiengi", "Chilanga", "Chililabombwe", "Chilubi", "Chingola", "Chinsali", "Chinyingi", "Chipata", "Chirundu", "Chisamba", "Choma", "Gwembe", "Isoka", "Kabompo", "Kabwe", "Kafue", "Kafulwe", "Kalabo", "Kalene Hill", "Kalomo", "Kalulushi", "Kanyembo", "Kaoma", "Kapiri Mposhi", "Kasama", "Kasempa", "Kashikishi", "Kataba", "Katete", "Kawambwa", "Kazembe", "Kazungula", "Kitwe", "Livingstone", "Luangwa", "Luanshya", "Lufwanyama", "Lukulu", "Lundazi", "Lusaka", "Macha Mission", "Makeni", "Mansa", "Mazabuka", "Mbala", "Mbereshi", "Mfuwe", "Milenge", "Misisi", "Mkushi", "Mongu", "Monze", "Mpika", "Mporokoso", "Mpulungu", "Mufulira", "Mumbwa", "Muyombe", "Mwinilunga", "Nakonde", "Nchelenge", "Ndola", "Ngoma", "Nkana", "Nseluka", "Pemba", "Petauke", "Samfya", "Senanga", "Serenje", "Sesheke", "Shiwa Ngandu", "Siavonga", "Sikalongo", "Sinazongwe", "Solwezi", "Zambezi", "Zimba")
        options.forEach {
            sourceAddList.add(SourceAddress(it))
        }
    }


    class SourceAddressAdapter(private var sourceAdds: ArrayList<SourceAddress>) : RecyclerView.Adapter<SourceAddressAdapter.MyAddressHolder>(){

        inner class MyAddressHolder(itemView : View):RecyclerView.ViewHolder(itemView){
            val sourceAddTV : TextView = itemView.findViewById(R.id.sourceAddTV)
            val cardView = itemView.findViewById<CardView>(R.id.myCard)
            init {
                itemView.setOnClickListener { v:View->
                    val positionVal = sourceAddTV.text.toString()
                    val intent = Intent(itemView.context, LocationActivity::class.java)
                    intent.putExtra("source", positionVal)
                    //itemView.context.startActivity(intent)
                    val app = itemView.context as AppCompatActivity
                    app.startActivityForResult(intent, 123)
//                    app.setResult(RESULT_OK)
//                    Toast.makeText(itemView.context, "here finished", Toast.LENGTH_SHORT).show()
//                    app.finish()
                }
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAddressHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.source_add_item, parent, false)
            return MyAddressHolder(itemView)
        }

        override fun onBindViewHolder(holder: MyAddressHolder, position: Int) {
            holder.sourceAddTV.text = sourceAdds[position].source
            holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context, R.anim.animation_xml))

        }

        override fun getItemCount(): Int {
            return sourceAdds.size
        }

        fun setFilteredList(list: ArrayList<SourceAddress>){
            sourceAdds = list
            notifyDataSetChanged()
        }

    }
}