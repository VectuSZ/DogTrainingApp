package uk.ac.aber.dcs.cs31620.healthypaws.datasource.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uk.ac.aber.dcs.cs31620.healthypaws.model.Exercise

object ExerciseSetConverter {
    @TypeConverter
    @JvmStatic
    fun fromSet(set: Set<Exercise>): String {
        val gson = Gson()
        val json = gson.toJson(set)
        return json
    }

    @TypeConverter
    @JvmStatic
    fun toSet(json: String): Set<Exercise> {
        val gson = Gson()
        val type = object : TypeToken<Set<Exercise>>() {}.type
        val set = gson.fromJson<Set<Exercise>>(json, type)
        return set
    }
}