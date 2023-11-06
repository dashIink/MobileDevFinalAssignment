package com.example.mobileappfinal

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class HomeScreenBasic : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen_basic)
        val acceptButton = findViewById<Button>(R.id.acceptButton)
        val rejectButton = findViewById<Button>(R.id.rejectButton)
        val activeCallsList = findViewById<ListView>(R.id.activeCallsList)
        val callData = arrayOf("Call 1", "Call 2", "Call 3", "Call 4", "Call 5")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, callData)
        activeCallsList.adapter = adapter


        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(object : OnMapReadyCallback {
            override fun onMapReady(googleMap: GoogleMap) {
                googleMap?.let {
                    val latitude = 43.9448472
                    val longitude = -78.8917028
                    val zoomLevel = 12.0
                    val location = LatLng(latitude, longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel.toFloat()))
                }
            }
        })
    }
}
