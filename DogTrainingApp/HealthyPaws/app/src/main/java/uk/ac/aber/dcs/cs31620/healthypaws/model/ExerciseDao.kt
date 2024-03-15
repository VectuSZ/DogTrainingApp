package uk.ac.aber.dcs.cs31620.healthypaws.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insertExercise(exercise: Exercise)

    @Insert
    suspend fun insertMultipleExercises(exerciseList: List<Exercise>)

    @Update
    suspend fun updateExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Query("DELETE FROM exercises")
    suspend fun deleteAll()

    @Query("SELECT * FROM exercises")
    fun getAllExercises(): LiveData<List<Exercise>>

    @Query("""SELECT * FROM exercises WHERE difficulty = :difficulty""")
    fun getExercises(
        difficulty: Difficulty
    ): LiveData<List<Exercise>>
}