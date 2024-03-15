package uk.ac.aber.dcs.cs31620.healthypaws.datasource.util

import androidx.room.TypeConverter
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object LocalTimeConverter {
    private val formatter = DateTimeFormatter.ISO_LOCAL_TIME

    @TypeConverter
    @JvmStatic
    fun fromTime(time: LocalTime?): String? {
        return time?.format(formatter)
    }

    @TypeConverter
    @JvmStatic
    fun toTime(timeString: String?): LocalTime? {
        return timeString?.let { LocalTime.parse(it, formatter) }
    }
}