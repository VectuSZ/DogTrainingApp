package uk.ac.aber.dcs.cs31620.healthypaws.model

import androidx.annotation.NonNull
import androidx.room.*
import uk.ac.aber.dcs.cs31620.healthypaws.datasource.util.ExerciseSetConverter
import uk.ac.aber.dcs.cs31620.healthypaws.datasource.util.LocalDateConverter
import java.time.LocalDate

@Entity(tableName = "dogs")
@TypeConverters(ExerciseSetConverter::class, LocalDateConverter::class)
data class Dog(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0,
    var name: String,
    var breed: String,
    var dateOfBirth: LocalDate,
    var knownCommands: Set<Exercise>,
    @ColumnInfo(name = "main_image_path")
    var imagePath: String = "",
) {
    fun addExercise(exercise: Exercise){
        knownCommands = knownCommands.toMutableSet().apply { add(exercise) }
    }
}

