package com.example.mobileappfinal

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck
import java.util.UUID

class FireCallDetectionService : Service()  {
    override fun onCreate() {

        Log.d("Service", "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Background processing logic
        Log.d("Help", "ME")
        var client = Mqtt5Client.builder()
            .identifier(UUID.randomUUID().toString())
            .serverHost("867b6fe36afb40469e3c4c1b6d61f583.s2.eu.hivemq.cloud")
            .serverPort(8883)
            .sslWithDefaultConfig()
            .buildAsync()

        client.connectWith()
            .simpleAuth()
            .username("hivemq.webclient.1698987558233")
            .password("2u,SovbR9:ih13T@B.NW".toByteArray())
            .applySimpleAuth()
            .send()
            .whenComplete { connAck: Mqtt5ConnAck?, throwable: Throwable? ->
                Log.d("Sub Unsuccessful", throwable.toString())
                Log.d("ConAck", connAck.toString())
                client.subscribeWith()
                    .topicFilter("Test")
                    .callback {
                        val messageContent = String(it.payloadAsBytes)
                        Log.d("WE GOT A MESSAGE", messageContent)
                        val notificationManager = createNotificationChannel()
                        if (notificationManager != null) {
                            CreateNotification(notificationManager, messageContent)
                        }


                    }
                    .send()
                    .whenComplete { subAck, throwable ->
                        if (throwable != null) {
                            Log.d("Connected", "Connected, failed to subscribe")
                        } else {
                            // Handle successful subscription, e.g. logging or incrementing a metric
                            Log.d("Sub Successful", "Ok we got this far")
                        }
                    }
            }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    fun CreateNotification(notificationManager: NotificationManager, callID : String) {

        val bundle = Bundle()
        bundle.putString("ID", callID)
        Log.d("Bundle", callID)
        bundle.putString("Level", "Basic")
        val intent = Intent(this, AlertDetails::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtras(bundle)
            action = "com.example.mobileappfinal.ACTION_VIEW_ALERT_DETAILS"
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        var builder = this.let {
            NotificationCompat.Builder(it, "1000")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Fire Call Emergency")
                .setContentText("A call has come in for your jurisdiction")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setChannelId("1000")
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText("Please enter the app to view details"))
                .build()
        }
        notificationManager?.notify(1000, builder)

    }

    private fun createNotificationChannel(): NotificationManager? {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "NotificationTest"
            val descriptionText = "Sending Test Notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(1000.toString(), name, importance).apply {
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