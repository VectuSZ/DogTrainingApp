package uk.ac.aber.dcs.cs31620.healthypaws.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diets")
data class Diet (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0,
    var name: String,
    var description: String,
)