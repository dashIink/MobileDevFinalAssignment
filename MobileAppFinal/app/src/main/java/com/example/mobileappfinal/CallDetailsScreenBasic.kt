package com.example.mobileappfinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.Button
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

class CallDetailsScreenBasic : AppCompatActivity() {
    val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_details_screen_basic)

        // Find the SupportMapFragment
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment

        val bundle = intent.extras
        val lat = bundle?.getString("lat")
        val lon = bundle?.getString("lon")
        val type = bundle?.getString("type")
        val report = bundle?.getString("report")
        val callID = bundle?.getString("callID")
        val confirmButton: Button = findViewById(R.id.Cancel)

        confirmButton.setOnClickListener {
            if (callID != null) {
                showConfirmationDialog(callID)
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

        findViewById<TextView>(R.id.textView3).text = "Please Report to Station: "+report
        findViewById<TextView>(R.id.location).text = "Location: "+ lat+", "+lon
        findViewById<TextView>(R.id.type).text = "Type: "+ type
        // Modify this to get stations from database through HTML call
        findViewById<TextView>(R.id.report).text = "Reporting Stations: "+ report

        run("http://10.0.2.2/FireFighterTestServer/FireFighterAPI.php?function=UpdateStat&ID=${permissionLevel.id}&CallID=$callID&Status=Accepted")
    }

    private fun showConfirmationDialog(callID: String) {
        val builder = AlertDialog.Builder(this)

        // Set the dialog title and message
        builder.setTitle("Confirmation")
            .setMessage("Are you sure you want stop responding to this call?")

        // Set positive button
        builder.setPositiveButton("Yes") { dialog, which ->
            run("http://10.0.2.2/FireFighterTestServer/FireFighterAPI.php?function=UpdateStat&ID=${permissionLevel.id}&CallID=$callID&Status=Canceled")
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
    fun run(url: String) {
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
                        runOnUiThread {
                            Toast.makeText(applicationContext, "No Active Calls", Toast.LENGTH_LONG).show()
                        }
                    }


                    // Now `itemList` contains the parsed data
                    // Do something with the array, e.g., update UI or store it in a variable
                    // Example: findViewById<TextView>(R.id.location).text = itemList.toString()

                }



            }
        })

    }
}
