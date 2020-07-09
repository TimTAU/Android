package de.fh_aachen.android.unit04.notifications_demo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

private const val CHANNEL_ID = "de.fh_aachen.android.CHANNEL_ID"
private const val CHANNEL_NAME = "de.fh_aachen.android.CHANNEL_NAME"

class MainActivity : AppCompatActivity() {

    private lateinit var channel: NotificationChannel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buildChannel()
    }

    fun notify1(v: View?) {
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .notify(0,NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("My Notification")
                        .setContentText("HuHu!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .build()
                )
    }

    private var notifyId = 0
    private val description = arrayOf("wichtige", "erste", "interessante", "ueberfluessige")

    fun notify2(v: View?) {
        ++notifyId
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .notify(notifyId,NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Notification No $notifyId")
                        .setContentText("Eine " + description[notifyId % 4] + " Botschaft!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .build()
                )
    }

    private fun buildChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
        }
    }
}
