package com.example.mobileappfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.mobileappfinal.dev.FireCallDetection

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(this, "Welcome to the App", Toast.LENGTH_LONG).show()

        findViewById<Button>(R.id.uiButton1).setOnClickListener {
            val intent = Intent(this, HomeScreenBasic::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.uiButton2).setOnClickListener {
            val intent = Intent(this, CallDetailsScreenBasic::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.uiButton3).setOnClickListener {
            val intent = Intent(this, HomeScreenCaptain::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.uiButton4).setOnClickListener {
            val intent = Intent(this, CallDetailsScreenCaptain::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.uiButton5).setOnClickListener {
            val intent = Intent(this, DashboardScreenDispatch::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.uiButton6).setOnClickListener {
            val intent = Intent(this, FirefighterStatsScreenMunicipality::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.uiButton7).setOnClickListener {
            val intent = Intent(this, ListVideosActivity::class.java)
            startActivity(intent)
        }
    }
}