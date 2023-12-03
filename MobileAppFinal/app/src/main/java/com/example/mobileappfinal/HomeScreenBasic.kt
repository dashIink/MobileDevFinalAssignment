package com.example.mobileappfinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

class HomeScreenBasic : AppCompatActivity() {
    val client = OkHttpClient()
    var callData = arrayListOf<String>()
    val callList = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen_basic)


        val activeCallsList = findViewById<ListView>(R.id.activeCallsList)
        run("http://10.0.2.2/FireFighterTestServer/FirefighterAPI.php?function=currentcalls")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, callData)
        activeCallsList.adapter = adapter
        activeCallsList.setOnItemClickListener { parent, view, position, id ->
            // Handle item click here
            val clickedItem = callList[position]
            val callID = callData[position]
            // Do something with the clicked item
            //Pick Up tomorrow, Send to alert Details page with call ID, get info from server
            val bundle = Bundle()
            bundle.putString("ID", callID)
            bundle.putString("Level", permissionLevel.permissionLevel)


            val intent = Intent(this, AlertDetails::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.notificationBadge).setOnClickListener(){
            val intent = Intent(this, DisplayVideoActivity::class.java)
            startActivity(intent)
        }


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

                        val callDetailsList = mutableListOf<JSONArray>()
                        val callDetailsArray = jsonArray.getJSONArray(1)

                            // Add the entire inner array to callDetailsList
                        callDetailsList.add(callDetailsArray)

                        Log.d("Tag", callDetailsList[0].length().toString())
                        val innerArray = callDetailsList[0].getJSONArray(0)
                        Log.d("You're It", innerArray.getString(0))

                        for (i in 0 until callDetailsList[0].length()){
                            val innerArray = callDetailsList[0].getJSONArray(i)
                            callList.add(innerArray.getString(5)+": Sent in at "+ innerArray.getString(3))
                            callData.add(innerArray.getString(0))
                        }

                    runOnUiThread{
                        val activeCallsList = findViewById<ListView>(R.id.activeCallsList)
                        val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, callList)
                        activeCallsList.adapter = adapter
                    }

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
