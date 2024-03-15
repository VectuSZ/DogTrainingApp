package uk.ac.aber.dcs.cs31620.healthypaws.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DietDao {
    @Insert
    suspend fun insertDiet(diet: Diet)

    @Insert
    suspend fun insertMultipleDiets(dietsList: List<Diet>)

    @Update
    suspend fun updateDiet(diet: Diet)

    @Delete
    suspend fun deleteDiet(diet: Diet)

    @Query("DELETE FROM diets")
    suspend fun deleteAll()

    @Query("SELECT * FROM diets")
    fun getAllDiets(): LiveData<List<Diet>>
}