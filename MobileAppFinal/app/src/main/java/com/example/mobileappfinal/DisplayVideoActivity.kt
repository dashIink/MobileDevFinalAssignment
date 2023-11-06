package com.example.mobileappfinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebView
import com.example.mobileappfinal.R

class DisplayVideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_video)
        val videoWebView = findViewById<WebView>(R.id.videoWebView)
        val webSettings = videoWebView.settings
        webSettings.javaScriptEnabled = true
        val videoUrl = intent.getStringExtra("VIDEO_URL")
        if (videoUrl != null) {
            videoWebView.loadUrl(videoUrl)
        } else {
            finish()
        }
    }
}
