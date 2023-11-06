package com.example.mobileappfinal

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class CallDetailsScreenCaptain : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_details_screen_captain)
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
