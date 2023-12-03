package com.example.mobileappfinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebView

class DisplayVideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_video)
        val videoWebView = findViewById<WebView>(R.id.videoWebView)
        val webSettings = videoWebView.settings
        webSettings.javaScriptEnabled = true
        val videoUrl = "https://www.youtube.com/embed/Zx_jHmlXJlo?si=kLwfw5UXsxlvDPLo"
        if (videoUrl != null) {
            videoWebView.loadUrl(videoUrl)
        } else {
            finish()
        }
    }
}
