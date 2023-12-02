package com.example.mobileappfinal

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
<<<<<<< Updated upstream
=======
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException
>>>>>>> Stashed changes

class LoginActivity : AppCompatActivity() {
    val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun onLoginButtonClick(view: View) {
        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)

        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (isDevCredentials(username, password)) {
            val intent = Intent(this, SecretButtonsActivity::class.java)
            startActivity(intent)
        }
        run("http://10.0.2.2/FireFighterTestServer/FirefighterAPI.php?user=$username&pass=$password&function=auth")


    }

    private fun isDevCredentials(username: String, password: String): Boolean {
        return username == "dev" && password == "dev"
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
                        val serviceIntent = Intent(applicationContext, FireCallDetectionService::class.java)
                        startService(serviceIntent)
                        permissionLevel.id = jsonArray[1].toString()
                        if (jsonArray[2] == "Basic"){
                            val intent = Intent(applicationContext, HomeScreenBasic::class.java)
                            permissionLevel.permissionLevel = "Basic"
                            startActivity(intent)
                        }
                        else if(jsonArray[2] == "Captain"){
                            val intent = Intent(applicationContext, HomeScreenCaptain::class.java)
                            permissionLevel.permissionLevel = "Captain"
                            startActivity(intent)
                        }

                    }
                    else{
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Invalid login credentials", Toast.LENGTH_LONG).show()
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
