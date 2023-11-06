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

}