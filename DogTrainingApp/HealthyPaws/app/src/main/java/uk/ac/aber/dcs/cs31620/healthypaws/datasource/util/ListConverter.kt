package uk.ac.aber.dcs.cs31620.healthypaws.datasource.util

import androidx.room.TypeConverter

object ListConverter {
    @TypeConverter
    @JvmStatic
    fun fromList(list: List<String>) : String {
        return list.joinToString(";")
    }

    @TypeConverter
    @JvmStatic
    fun toList(string: String): List<String> {
        return string.split(";")
    }
}