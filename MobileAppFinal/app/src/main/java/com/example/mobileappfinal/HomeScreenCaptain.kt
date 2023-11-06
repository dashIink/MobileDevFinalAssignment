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
import android.widget.ArrayAdapter
import android.widget.TextView

class HomeScreenCaptain : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen_captain)

        val acceptButton = findViewById<Button>(R.id.acceptButton)
        val rejectButton = findViewById<Button>(R.id.rejectButton)
        val activeCallsList = findViewById<ListView>(R.id.activeCallsList)
        val requestFirefightersButton = findViewById<Button>(R.id.requestFirefightersButton)
        val notificationBadge = findViewById<TextView>(R.id.notificationBadge)

        val callData = arrayOf("Call 1 - 5 firefighters", "Call 2 - 3 firefighters", "Call 3 - 7 firefighters")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, callData)
        activeCallsList.adapter = adapter

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(object : OnMapReadyCallback {
            override fun onMapReady(googleMap: GoogleMap) {
                googleMap?.let {
                    val emergencyLocation = LatLng(43.9448472, -78.8917028)
                    val zoomLevel = 12.0
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(emergencyLocation, zoomLevel.toFloat()))
                }
            }
        })
    }
}
