package uk.ac.aber.dcs.cs31620.healthypaws.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import uk.ac.aber.dcs.cs31620.healthypaws.R

class ReminderBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title")
        val notificationManager = NotificationManagerCompat.from(context)
        val notificationId = System.currentTimeMillis().toInt()

        val notification = NotificationCompat.Builder(context, "event_channel_id")
            .setSmallIcon(R.drawable.dog)
            .setContentTitle("Event Reminder: $title")
            .setContentText("This event is coming up soon.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(notificationId, notification)
    }

}