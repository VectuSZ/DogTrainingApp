package uk.ac.aber.dcs.cs31620.healthypaws.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DogDao {
    @Insert
    suspend fun insertSingleDog(dog: Dog)

    @Insert
    suspend fun insertMultipleDogs(dogList: List<Dog>)

    @Update
    suspend fun updateDog(dog: Dog)

    @Delete
    suspend fun deleteDog(dog: Dog)

    @Query("DELETE FROM dogs")
    suspend fun deleteAll()

    @Query("SELECT * FROM dogs")
    fun getAllDogs(): LiveData<List<Dog>>

}