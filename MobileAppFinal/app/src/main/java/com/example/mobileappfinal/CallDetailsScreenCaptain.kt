package com.example.mobileappfinal

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

class ServerRequestHandler(private val callback: () -> Unit) {
    private val handler = Handler()
    private val interval: Long = 3000 // 1 second

    private val runnable: Runnable = object : Runnable {
        override fun run() {
            // Perform your server request here
            callback.invoke()

            // Repeat the task after the specified interval
            handler.postDelayed(this, interval)
        }
    }

    fun start() {
        // Start the periodic task
        handler.postDelayed(runnable, interval)
    }

    fun stop() {
        // Stop the periodic task
        handler.removeCallbacks(runnable)
    }
}


class CallDetailsScreenCaptain : AppCompatActivity() {
    val client = OkHttpClient()
    var callID: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_details_screen_captain)

        // Find the SupportMapFragment
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment

        val bundle = intent.extras
        val lat = bundle?.getString("lat")
        val lon = bundle?.getString("lon")
        val type = bundle?.getString("type")
        val report = bundle?.getString("report")
        callID = bundle?.getString("callID").toString()
        val confirmButton: Button = findViewById(R.id.endCallButton)

        confirmButton.setOnClickListener {
           if (callID != null) {
                showConfirmationDialog(callID)
            }
        }
        findViewById<Button>(R.id.sendChatButton).setOnClickListener {
            var message = findViewById<EditText>(R.id.chatInputEditText).text.toString()
            if(message != ""){
                sendMessage("http://10.0.2.2/FireFighterTestServer/FireFighterAPI.php?function=SendMessage&ID=${permissionLevel.id}&Message=${message}")
            }
        }

        // Initialize the map
        mapFragment.getMapAsync(object : OnMapReadyCallback {
            override fun onMapReady(googleMap: GoogleMap) {
                // Check if the GoogleMap object is not null
                googleMap?.let {
                    // Define the location (latitude and longitude) of the emergency
                    val emergencyLocation =
                        lat?.let { it1 -> lon?.let { it2 -> LatLng(it1.toDouble(), it2.toDouble()) } } // Replace with the desired location

                    // Define a zoom level (2.0 is world view, 21.0 is street view)
                    val zoomLevel = 12.0 // Adjust this value as needed

                    // Move the camera to the specified location with the desired zoom level
                    emergencyLocation?.let { it1 ->
                        CameraUpdateFactory.newLatLngZoom(
                            it1, zoomLevel.toFloat())
                    }?.let { it2 -> googleMap.moveCamera(it2) }
                }
            }
        })

        //findViewById<TextView>(R.id.textView3).text = "Please Report to Station: "+report
        //findViewById<TextView>(R.id.location).text = "Location: "+ lat+", "+lon
        //findViewById<TextView>(R.id.type).text = "Type: "+ type
        // Modify this to get stations from database through HTML call
        //findViewById<TextView>(R.id.report).text = "Reporting Stations: "+ report

        runStatus("http://10.0.2.2/FireFighterTestServer/FireFighterAPI.php?function=UpdateStat&ID=${permissionLevel.id}&CallID=$callID&Status=Accepted")
        serverRequestHandler.start()


    }
    override fun onDestroy() {
        super.onDestroy()
        // Stop the periodic task when the activity is destroyed
        serverRequestHandler.stop()
    }

    private fun showConfirmationDialog(callID: String) {
        val builder = AlertDialog.Builder(this)

        // Set the dialog title and message
        builder.setTitle("Confirmation")
            .setMessage("Are you sure you want stop responding to this call?")

        // Set positive button
        builder.setPositiveButton("Yes") { dialog, which ->
            runStatus("http://10.0.2.2/FireFighterTestServer/FireFighterAPI.php?function=UpdateStat&ID=${permissionLevel.id}&CallID=$callID&Status=Canceled")
            val intent = Intent(this, HomeScreenBasic::class.java)
            startActivity(intent)
        }

        // Set negative button
        builder.setNegativeButton("No") { dialog, which ->
            // Action to be performed if "No" is clicked
            // For example, you can close the dialog or do nothing
            // ...
        }

        // Create and show the dialog
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    fun runStatus(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("HTTP request failed", e.toString())}
            override fun onResponse(call: Call, response: Response) {
                response.body()?.let {
                    val jsonString = it.string()
                    Log.d("HTTP request success", jsonString)

                    val jsonArray = JSONArray(jsonString)
                    Log.d("help", jsonArray.toString())
                    if (jsonArray[0] == "Confirm"){


                    }
                    else{

                    }


                    // Now `itemList` contains the parsed data
                    // Do something with the array, e.g., update UI or store it in a variable
                    // Example: findViewById<TextView>(R.id.location).text = itemList.toString()

                }



            }
        })

    }
    private val serverRequestHandler = ServerRequestHandler {
        getFireFighters("http://10.0.2.2/FireFighterTestServer/FireFighterAPI.php?function=GetFireFighters&CallID=$callID")
        getMessage("http://10.0.2.2/FireFighterTestServer/FireFighterAPI.php?function=GetMessage&ID=${permissionLevel.id}")
    }

    fun getFireFighters(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("HTTP request failed", e.toString())}
            override fun onResponse(call: Call, response: Response) {
                response.body()?.let {
                    val jsonString = it.string()
                    Log.d("HTTP request success", jsonString)

                    val jsonArray = JSONArray(jsonString)
                    Log.d("help", jsonArray.toString())
                    if (jsonArray[0] != null){
                        val responders = mutableListOf<String>()
                        for (i in 0 until jsonArray.length()){

                            val callDetailsArray = jsonArray.getJSONArray(i)

                            responders.add("Name: "+callDetailsArray[5].toString()+", Station: "+callDetailsArray[6].toString())
                        }

                        Log.d("Tag", responders.toString())

                        runOnUiThread{
                            val activeCallsList = findViewById<ListView>(R.id.firefightersList)
                            val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, responders)
                            activeCallsList.adapter = adapter
                        }

                    }
                    else{

                    }


                    // Now `itemList` contains the parsed data
                    // Do something with the array, e.g., update UI or store it in a variable
                    // Example: findViewById<TextView>(R.id.location).text = itemList.toString()

                }



            }
        })

    }
    fun sendMessage(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("HTTP request failed", e.toString())}
            override fun onResponse(call: Call, response: Response) {
                response.body()?.let {
                    val jsonString = it.string()
                    Log.d("HTTP request success", jsonString)

                    val jsonArray = JSONArray(jsonString)
                    Log.d("help", jsonArray.toString())
                    if (jsonArray[0] != null){


                    }
                    else{

                    }


                    // Now `itemList` contains the parsed data
                    // Do something with the array, e.g., update UI or store it in a variable
                    // Example: findViewById<TextView>(R.id.location).text = itemList.toString()

                }



            }
        })

    }
    fun getMessage(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("HTTP request failed", e.toString())}
            override fun onResponse(call: Call, response: Response) {
                response.body()?.let {
                    val jsonString = it.string()
                    Log.d("HTTP request success", jsonString)

                    val jsonArray = JSONArray(jsonString)
                    Log.d("help", jsonArray.toString())
                    if (jsonArray[0] != null){
                        val messages = mutableListOf<String>()
                        for (i in (jsonArray.length() - 1) downTo 0) {
                            messages.add(jsonArray.getString(i))
                        }
                        Log.d("message", messages.toString())



                        runOnUiThread{
                            val activeCallsList = findViewById<ListView>(R.id.chatmessageslist)
                            val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, messages)
                            activeCallsList.adapter = adapter
                        }

                    }
                    else{

                    }


                    // Now `itemList` contains the parsed data
                    // Do something with the array, e.g., update UI or store it in a variable
                    // Example: findViewById<TextView>(R.id.location).text = itemList.toString()

                }



            }
        })

    }
}