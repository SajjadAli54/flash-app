package com.example.datademo.data

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DatabaseHandler{

    private constructor(){

    }

    companion object{
        private val databaseHandler = DatabaseHandler()

        fun getInstance(context: Context): DatabaseHandler{
            databaseHandler.context = context
            return databaseHandler
        }
    }


    fun addDriver(driver: Driver, map: HashMap<String, Uri?>){
        if(driverPresent(driver.username)){
            Toast.makeText(context, "username not available", Toast.LENGTH_LONG).show()
            return
        }
        Thread{
            db.collection(driversCollection).document(driver.username).set(driver)
                .addOnSuccessListener {
                    Toast.makeText(context, "Registered successfully!", Toast.LENGTH_LONG).show()
                    driversMap[driver.username] = driver

                    val ref: StorageReference = reference.child(driversCollection).child(driver.username)
                    uploader(ref, map)
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
                }
        }.start()
    }

    fun downloadDriverImages(username: String, map: HashMap<String, ImageView>){
        val ref = pathReference.child(driversCollection).child(username)
        downloadImages(ref, map)
    }

    fun getDrivers(): Array<Driver>{
        return driversMap.values.toTypedArray()
    }

    fun getDriver(username: String): Driver{
        return driversMap[username]!!
    }

    fun driverPresent(username: String): Boolean{
        return driversMap.containsKey(username)
    }

    fun existsDriver(username: String, password: String): Boolean{
        return driverPresent(username) &&
                driversMap[username]!!.password.equals(password)
    }

    fun addPassenger(passenger: Passenger, map: HashMap<String, Uri?>){
        if(passengerPresent(passenger.username)){
            Toast.makeText(context, "username not available", Toast.LENGTH_LONG).show()
            return
        }
        Thread{
            db.collection(passengersCollection).document(passenger.username)
                .set(passenger).addOnSuccessListener {
                    Toast.makeText(context, "Registered successfully!", Toast.LENGTH_LONG).show()
                    passengerMap[passenger.username] = passenger

                    val ref: StorageReference = reference.child(passengersCollection).child(passenger.username)
                    uploader(ref, map)
                }.addOnFailureListener {
                    Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
                }
        }.start()
    }

    fun getPassengers(): Array<Passenger>{
        return passengerMap.values.toTypedArray()
    }

    fun passengerPresent(username: String): Boolean{
        return passengerMap.containsKey(username)
    }


    fun existsPassenger(username: String, password: String): Boolean{
        return passengerPresent(username) &&
                passengerMap[username]!!.password.equals(password)
    }

    fun getPassenger(username: String): Passenger{
        return passengerMap[username]!!
    }


    fun downloadPassengerImages(username: String, map: HashMap<String, ImageView>){
        val ref = pathReference.child(passengersCollection).child(username)
        downloadImages(ref, map)
    }

    fun addVehicle(vehicle: Vehicle, map: HashMap<String, Uri?>){
        if(vehiclePresent(vehicle.username)){
            Toast.makeText(context, "Already registered!", Toast.LENGTH_LONG).show()
            return
        }
        Thread{
            db.collection(vehiclesCollection).document(vehicle.username).set(vehicle)
                .addOnSuccessListener {
                    Toast.makeText(context, "Registered successfully!", Toast.LENGTH_LONG).show()
                    vehiclesMap[vehicle.username] = vehicle

                    val ref: StorageReference = reference.child(vehiclesCollection).child(vehicle.username)
                    uploader(ref, map)
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
                }
        }.start()
    }

    fun getVehicles(): Array<Vehicle>{
        return vehiclesMap.values.toTypedArray()
    }

    fun getVehicle(username: String): Vehicle{
        return vehiclesMap[username]!!
    }

    fun vehiclePresent(username: String): Boolean{
        return vehiclesMap.containsKey(username)
    }

    fun downloadVehicleImages(username: String, map: HashMap<String, ImageView>){
        val ref = pathReference.child(vehiclesCollection).child(username)

        downloadImages(ref, map)
    }

    private fun uploader(ref: StorageReference, cv: HashMap<String, Uri?>){
        for(key in cv.keys)
            uploadImage(ref, key, cv[key])
    }

    private fun uploadImage
                (ref: StorageReference,image: String, uriPath: Uri?){
        ref.child(image).putFile(uriPath!!)
            .addOnSuccessListener {
                Toast.makeText(context, "Images saved", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Images could not be saved", Toast.LENGTH_SHORT).show()
            }
    }

    private fun populateDrivers(){
        Thread{
            db.collection(driversCollection)
                .get()
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        for(doc in it.result){
                            val type: Class<Driver> = Driver::class.java
                            val driver: Driver = doc.toObject(type)
                            driversMap[driver.username] = driver
                        }
                    }
                }
        }.start()
    }

    private fun populatePassengers(){
        Thread{
            db.collection(passengersCollection)
                .get()
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        for(doc in it.result){
                            val type = Passenger::class.java
                            val passenger: Passenger = doc.toObject(type)
                            passengerMap[passenger.username] = passenger
                        }
                    }
                }
        }.start()
    }

    private fun populateVehicles(){
        Thread{
            db.collection(vehiclesCollection)
                .get()
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        for(doc in it.result){
                            val type = Vehicle::class.java
                            val vehicle: Vehicle = doc.toObject(type)
                            vehiclesMap[vehicle.plateNumber] = vehicle
                        }
                    }
                }
        }.start()
    }

    private fun downloadImages(ref: StorageReference, map: HashMap<String, ImageView>){
        val keys = map.keys
        for(key in keys)
            Glide.with(context).load(ref.child(key)).into(map[key] as ImageView)
    }

    val driversCollection = "drivers"
    val passengersCollection = "passengers"
    val vehiclesCollection = "vehicles"
    val imagesCollection = "images"

    val db: FirebaseFirestore = Firebase.firestore
    lateinit var context: Context

    val driversMap = hashMapOf<String, Driver>()
    val passengerMap = hashMapOf<String, Passenger>()
    val vehiclesMap = hashMapOf<String, Vehicle>()

    var reference: StorageReference
    var pathReference: StorageReference

    init {
        val storage: FirebaseStorage = FirebaseStorage.getInstance()

        reference = storage.getReference(imagesCollection)
        pathReference = storage.getReferenceFromUrl("gs://login-project-23bf7.appspot.com").child(imagesCollection)

        populateDrivers()
        populatePassengers()
        populateVehicles()
    }
}