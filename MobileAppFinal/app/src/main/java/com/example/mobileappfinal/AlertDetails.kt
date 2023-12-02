<<<<<<< Updated upstream
package com.example.mobileappfinal

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import org.json.JSONObject

class AlertDetails : AppCompatActivity() {
    val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alert_details)

        run("http://10.0.2.2/FireFighterTestServer/FirefighterAPI.php")



    }
    fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("HTTP request failed", e.toString())}
            override fun onResponse(call: Call, response: Response) {
                response.body()?.let { Log.d("HTTP request success", it.string()) }

            }
        })
    }
    fun parseJSON(jsonString: String): JSONObject {
        // Create a JSONObject and pass the json string
        return JSONObject(jsonString)
    }

=======
package com.example.mobileappfinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONArray
import java.io.IOException
import org.json.JSONObject

class AlertDetails : AppCompatActivity() {
    val client = OkHttpClient()
    val itemList = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alert_details)
        val bundle = intent.extras
        var id = permissionLevel.id

        if (id != null) {
            Log.d("id", id)
        }


        val Level = permissionLevel.permissionLevel
        findViewById<Button>(R.id.button5).setOnClickListener {
            val result = itemList[2].split(",")
            val bundle = Bundle()
            bundle.putString("lat", result[0])
            bundle.putString("lon", result[1])
            bundle.putString("type", itemList[5])
            bundle.putString("report", itemList[6])
            bundle.putString("callID", itemList[0])

            val intent = Intent(this, CallDetailsScreenBasic::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        if (id != null){
            run("http://10.0.2.2/FireFighterTestServer/FireFighterAPI.php?function=CallByID&ID=$id")
        }
        findViewById<Button>(R.id.back).setOnClickListener {
            finish()
        }
        //run("http://10.0.2.2/FireFighterTestServer/FirefighterAPI.php")
    }

    fun updateUI() {
        findViewById<TextView>(R.id.location).text = "Location: " + itemList[2]
        findViewById<TextView>(R.id.type).text = "Type of call: " +itemList[5]
        findViewById<TextView>(R.id.report).text = "Reporting Stations: " +itemList[6]
        // Update other UI elements as needed
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
                    if (jsonArray[0] == "Confirm") {
                        val callDetailsArray = jsonArray.getJSONArray(1)

                        for (i in 0 until callDetailsArray.length()) {
                            val item = callDetailsArray.getString(i)
                            itemList.add(item)
                        }
                    }



                    // Now `itemList` contains the parsed data
                    // Do something with the array, e.g., update UI or store it in a variable
                    // Example: findViewById<TextView>(R.id.location).text = itemList.toString()
                    runOnUiThread { updateUI() }
                }



            }
        })
    }
    fun parseJSON(jsonString: String): JSONObject {
        // Create a JSONObject and pass the json string
        return JSONObject(jsonString)
    }

>>>>>>> Stashed changes
}