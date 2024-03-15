package uk.ac.aber.dcs.cs31620.healthypaws.datasource.util

import androidx.room.TypeConverter
import uk.ac.aber.dcs.cs31620.healthypaws.model.Difficulty

object DifficultyConverter {
    @TypeConverter
    @JvmStatic
    fun fromDifficulty(difficulty: Difficulty): String {
        return difficulty.name
    }

    @TypeConverter
    @JvmStatic
    fun toDifficulty(name: String): Difficulty {
        return Difficulty.valueOf(name)
    }
}