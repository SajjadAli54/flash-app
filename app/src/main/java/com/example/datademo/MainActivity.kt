package com.example.datademo

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.datademo.data.DatabaseHandler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun login(v: View){
        Toast.makeText(this, "Here ", Toast.LENGTH_LONG).show()

    }

    fun register(v:View){
        val intent : Intent = Intent(this, Role::class.java)
        startActivity(intent)
    }
}
