package com.example.mobileappfinal.dev

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mobileappfinal.R

class SoundPlayActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound_play)

        val playSoundButton = findViewById<Button>(R.id.playSoundButton)
        mediaPlayer = MediaPlayer.create(this, R.raw.sound_test)

        playSoundButton.setOnClickListener {
            if (mediaPlayer != null) {
                if (mediaPlayer!!.isPlaying) {
                    mediaPlayer!!.pause()
                    playSoundButton.text = "Play Sound"
                } else {
                    mediaPlayer!!.start()
                    playSoundButton.text = "Pause Sound"
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
