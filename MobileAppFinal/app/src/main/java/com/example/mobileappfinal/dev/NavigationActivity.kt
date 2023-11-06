package com.example.mobileappfinal.dev

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileappfinal.R
import java.io.IOException
import java.util.Locale

class NavigationActivity : AppCompatActivity() {
    private val latLongs = mutableListOf<Pair<Double, Double>>()
    private var latLongsIterator: Iterator<Pair<Double, Double>>? = null
    private val addresses = mutableListOf<String>()
    private var addressesIterator: Iterator<String>? = null
    private val searchStrings = mutableListOf<String>()
    private var searchStringsIterator: Iterator<String>? = null
    private var uri: Uri? = null

    fun getAddressLocation(address: String, geocoder: Geocoder): Pair<Double, Double>? {
        try {
            val addresses: List<Address> = geocoder.getFromLocationName(address, 1) as List<Address>
            if (addresses.isNotEmpty()) {
                val firstAddress = addresses[0]
                val latitude = firstAddress.latitude
                val longitude = firstAddress.longitude
                return Pair(latitude, longitude)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun createNavigationUri(
        navigationType: String,
        destinationLatitude: Double = 0.0,
        destinationLongitude: Double = 0.0,
        destinationAddress: String? = null,
        searchString: String? = null
    ): Uri? {
        when (navigationType) {
            "LATLONG" -> {
                return Uri.parse("google.navigation:q=$destinationLatitude,$destinationLongitude")
            }
            "ADDRESS" -> {
                val geocoder = Geocoder(this, Locale.getDefault())
                if (destinationAddress != null) {
                    val location = getAddressLocation(destinationAddress, geocoder)
                    if (location != null) {
                        val destinationLatitude = location.first
                        val destinationLongitude = location.second
                        return Uri.parse("google.navigation:q=$destinationLatitude,$destinationLongitude")
                    } else {
                        Toast.makeText(this, "Geocoder could not find lat/long for address: $destinationAddress", Toast.LENGTH_LONG).show()
                        return null
                    }
                } else {
                    Toast.makeText(this, "Invalid destination address", Toast.LENGTH_LONG).show()
                    return null
                }
            }
            "SEARCH" -> {
                return Uri.parse("google.navigation:q=$searchString")
            }
            else -> {
                finish()
            }
        }
        return null
    }


    fun startMapNavigation(uri: Uri) {
        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        } else {
            val browserIntent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(browserIntent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        val openMapsButton1 = findViewById<Button>(R.id.openMapsButton1)
        val openMapsButton2 = findViewById<Button>(R.id.openMapsButton2)
        val openMapsButton3 = findViewById<Button>(R.id.openMapsButton3)

        val items1 = arrayOf("7 min", "5 min", "3 min")
        val listView1 = findViewById<ListView>(R.id.listView1)
        val adapter1 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items1)
        val items2 = arrayOf("Fire Fighter #1", "Fire Fighter #2", "Fire Fighter #3")
        val listView2 = findViewById<ListView>(R.id.listView2)
        val adapter2 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items2)
        listView1.adapter = adapter1
        listView2.adapter = adapter2

        latLongs.add(Pair(44.389355, -79.690331))
        latLongs.add(Pair(44.166668, -77.383331))
        latLongs.add(Pair(43.683334, -79.76667))
        latLongs.add(Pair(43.166668, -80.25))
        latLongs.add(Pair(43.328674, -79.817734))
        latLongs.add(Pair(51.049999, -114.066666))
        addresses.add("300 Taunton Rd E, Oshawa, ON L1H 7K4")
        addresses.add("1393 Harmony Rd N Unit M2, Oshawa, ON L1H 7K5")
        addresses.add("1326 Simcoe St N, Oshawa, ON L1G 4X4")
        addresses.add("251 Ritson Rd N, Oshawa, ON L1G 0B9")
        addresses.add("123 Main Street, Your City, State")
        addresses.add("123 Main Street, Your City, State")
        searchStrings.add("Oshawa Walmart")
        searchStrings.add("Ajax Costco")
        searchStrings.add("Ontario Tech University")
        searchStrings.add("Canadian Tire Wilson Rd Oshawa")
        searchStrings.add("Oshawa Executive Airport")
        searchStrings.add("GM Oshawa Assembly")
        latLongsIterator = latLongs.iterator()
        addressesIterator = addresses.iterator()
        searchStringsIterator = searchStrings.iterator()

        openMapsButton1.setOnClickListener {
            if (latLongsIterator!!.hasNext()) {
                val latlong = latLongsIterator!!.next()
                uri = createNavigationUri(
                    navigationType = "LATLONG",
                    destinationLatitude = latlong.first,
                    destinationLongitude = latlong.second
                )
                uri?.let { it1 -> startMapNavigation(it1) }
            } else {
                latLongsIterator = latLongs.iterator()
            }
        }

        openMapsButton2.setOnClickListener {
            if (addressesIterator!!.hasNext()) {
                val address = addressesIterator!!.next()
                uri = createNavigationUri(
                    navigationType = "ADDRESS",
                    destinationAddress = address
                )
                uri?.let { it1 -> startMapNavigation(it1) }
            } else {
                addressesIterator = addresses.iterator()
            }
        }

        openMapsButton3.setOnClickListener {
            if (searchStringsIterator!!.hasNext()) {
                val searchString = searchStringsIterator!!.next()
                uri = createNavigationUri(
                    navigationType = "SEARCH",
                    searchString = searchString
                )
                uri?.let { it1 -> startMapNavigation(it1) }
            } else {
                searchStringsIterator = searchStrings.iterator()
            }
        }
    }
}
// val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/maps?daddr=$destinationLatitude,$destinationLongitude"))
