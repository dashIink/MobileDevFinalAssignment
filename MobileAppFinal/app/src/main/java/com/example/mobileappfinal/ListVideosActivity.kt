package com.example.mobileappfinal

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View

class ListVideosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_videos)

        val launchVideoButton = findViewById<View>(R.id.launchVideoButton)

        launchVideoButton.setOnClickListener {
            val videoUrl = "https://www.youtube.com/embed/l9PlNRSrzVs"
            val intent = Intent(this, DisplayVideoActivity::class.java)
            intent.putExtra("VIDEO_URL", videoUrl)
            startActivity(intent)
        }
    }
}
