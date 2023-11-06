package com.example.mobileappfinal

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class FirefighterStatsScreenMunicipality : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firefighter_stats_screen_municipality)

        val items1 = arrayOf("Firefighter 1", "Firefighter 2", "Firefighter 3")
        val listView1 = findViewById<ListView>(R.id.listView1)
        val adapter1 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items1)
        val items2 = arrayOf("Call 1", "Call 2", "Call 3")
        val listView2 = findViewById<ListView>(R.id.listView2)
        val adapter2 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items2)
        listView1.adapter = adapter1
        listView2.adapter = adapter2


//        val firefighterData = arrayOf("Firefighter 1", "Firefighter 2", "Firefighter 3")
//        val firefightersList = findViewById<ListView>(R.id.firefightersListView)
//        val firefightersAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, firefighterData)
//        val callData = arrayOf("Call 1", "Call 2", "Call 3")
//        val callsList = findViewById<ListView>(R.id.callsListView)
//        val callsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, callData)
//        firefightersList.adapter = firefightersAdapter
//        callsList.adapter = callsAdapter

    }
}

