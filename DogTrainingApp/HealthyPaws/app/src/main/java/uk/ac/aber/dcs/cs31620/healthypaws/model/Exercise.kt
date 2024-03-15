package uk.ac.aber.dcs.cs31620.healthypaws.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import uk.ac.aber.dcs.cs31620.healthypaws.datasource.util.ListConverter

@Entity(tableName = "exercises")
@TypeConverters(ListConverter::class)
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0,
    var name: String,
    var difficulty: Difficulty,
    var shortDescription: String,
    var longDescription: List<String>,
    var imagePathList: List<String>,
    var imagePath: String = "",
)