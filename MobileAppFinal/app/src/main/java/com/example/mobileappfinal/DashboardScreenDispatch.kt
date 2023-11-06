package com.example.mobileappfinal

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DashboardScreenDispatch : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_screen_dispatch)

        // Initialize the map
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(object : OnMapReadyCallback {
            override fun onMapReady(googleMap: GoogleMap) {
                // Check if the GoogleMap object is not null
                googleMap?.let {
                    // Define the location (latitude and longitude) of the area
                    val areaLocation = LatLng(43.9448472, -78.8917028) // Replace with the desired location

                    // Define a zoom level (2.0 is world view, 21.0 is street view)
                    val zoomLevel = 12.0 // Adjust this value as needed

                    // Add a marker to the map to indicate the area
                    googleMap.addMarker(MarkerOptions().position(areaLocation).title("Area"))

                    // Move the camera to the specified location with the desired zoom level
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(areaLocation, zoomLevel.toFloat()))
                }
            }
        })

        // TODO: Populate the lists and add button click listeners
        val activeCallsList = findViewById<ListView>(R.id.activeCallsList)
        val callData = arrayOf("Call 1", "Call 2", "Call 3", "Call 4", "Call 5")
        // Create an adapter and set it to the list view

        val availableFirefightersList = findViewById<ListView>(R.id.availableFirefightersList)
        val firefightersData = arrayOf("Firefighter 1", "Firefighter 2", "Firefighter 3")
        // Create an adapter and set it to the list view
    }
}
