package uk.ac.aber.dcs.cs31620.healthypaws.model

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import uk.ac.aber.dcs.cs31620.healthypaws.datasource.util.LocalDateConverter
import uk.ac.aber.dcs.cs31620.healthypaws.datasource.util.LocalTimeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

@Entity(tableName = "events")
@TypeConverters(LocalDateConverter::class, LocalTimeConverter::class)
data class CalendarEvent(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0,
    var title: String,
    var date: LocalDate,
    var time: LocalTime,
) {

    @SuppressLint("UnspecifiedImmutableFlag")
    fun scheduleNotifications(context: Context) {
        val intent = Intent(context, ReminderBroadcastReceiver::class.java)
        intent.putExtra("title", title)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val oneDayBefore =
            LocalDateTime.of(date, time).minusDays(1).atZone(ZoneId.systemDefault()).toInstant()
                .toEpochMilli()
        val fourHoursBefore =
            LocalDateTime.of(date, time).minusHours(4).atZone(ZoneId.systemDefault()).toInstant()
                .toEpochMilli()

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, oneDayBefore, pendingIntent)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, fourHoursBefore, pendingIntent)
    }
}