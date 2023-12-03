package com.example.mobileappfinal

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.NotificationCompat


//Don't worry about the stuff I have in here, this was mostly just a notification test thing, you can use this page
//for auth as it should be one of the first things they see
class SecretButtonsActivity : AppCompatActivity() {
    var CHANNEL_ID = "1000"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret_buttons)
        val notificationManager = createNotificationChannel()

        val intent = Intent(this, AlertDetails::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Notification Test")
            .setContentText("Please Help")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)
            .setChannelId(CHANNEL_ID)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("this is a much longer text that will run off the screen"))

            .build()


        findViewById<Button>(R.id.button).setOnClickListener {
            notificationManager?.notify(1000, builder)
            Log.d("HELP", "ME")
        }
        // notificationId is a unique int for each notification that you must define.

        findViewById<Button>(R.id.button2).setOnClickListener {
            val serviceIntent = Intent(this, FireCallDetectionService::class.java)
            startService(serviceIntent)
            //val intent = Intent(this, FireCallDetection::class.java)
            //startActivity(intent)
        }

        findViewById<Button>(R.id.button3).setOnClickListener {
            val intent = Intent(this, ListVideosActivity::class.java)
            startActivity(intent)
        }

    }
    private fun createNotificationChannel(): NotificationManager? {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "NotificationTest"
            val descriptionText = "Sending Test Notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            channel.canShowBadge()
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            return notificationManager
        }
        return null

    }
}