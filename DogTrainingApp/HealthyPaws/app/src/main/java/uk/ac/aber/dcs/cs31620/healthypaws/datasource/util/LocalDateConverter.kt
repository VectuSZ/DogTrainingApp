package uk.ac.aber.dcs.cs31620.healthypaws.datasource.util

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object LocalDateConverter {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    @JvmStatic
    fun fromDate(date: LocalDate?): String? {
        return date?.format(formatter)
    }

    @TypeConverter
    @JvmStatic
    fun toDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it, formatter) }
    }
}