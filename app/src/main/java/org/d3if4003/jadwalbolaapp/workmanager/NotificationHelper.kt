package org.d3if4003.jadwalbolaapp.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import org.d3if4003.jadwalbolaapp.MainActivity
import org.d3if4003.jadwalbolaapp.R
import org.d3if4003.jadwalbolaapp.util.Const.Companion.CHANNEL_ID
import org.d3if4003.jadwalbolaapp.util.Const.Companion.NOTIFICATION_ID

class NotificationHelper(val context: Context) {
    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT ).apply {
                description = "Jadwal Bola Channel Description"
            }
            val notificationManager =  context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    fun createNotification(title: String, message: String){
        // 1
        createNotificationChannel()
        // 2
        val intent = Intent(context, MainActivity:: class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        // 3
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        // 4
        val icon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_foreground)
        // 5
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        // 6
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
        
    }
}