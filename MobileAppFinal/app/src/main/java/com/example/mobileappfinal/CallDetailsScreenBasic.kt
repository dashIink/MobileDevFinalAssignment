package com.example.mobileappfinal

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import android.widget.ListView
import android.widget.ArrayAdapter

class CallDetailsScreenBasic : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_details_screen_basic)

        // Find the SupportMapFragment
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment

        // Initialize the map
        mapFragment.getMapAsync(object : OnMapReadyCallback {
            override fun onMapReady(googleMap: GoogleMap) {
                // Check if the GoogleMap object is not null
                googleMap?.let {
                    // Define the location (latitude and longitude) of the emergency
                    val emergencyLocation = LatLng(43.9448472, -78.8917028) // Replace with the desired location

                    // Define a zoom level (2.0 is world view, 21.0 is street view)
                    val zoomLevel = 12.0 // Adjust this value as needed

                    // Move the camera to the specified location with the desired zoom level
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(emergencyLocation, zoomLevel.toFloat()))
                }
            }
        })

        // Set up the ListView with call data
        val activeCallsList = findViewById<ListView>(R.id.activeCallsList)
        val callData = arrayOf("Call 1", "Call 2", "Call 3", "Call 4", "Call 5")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, callData)
        activeCallsList.adapter = adapter
    }
}
