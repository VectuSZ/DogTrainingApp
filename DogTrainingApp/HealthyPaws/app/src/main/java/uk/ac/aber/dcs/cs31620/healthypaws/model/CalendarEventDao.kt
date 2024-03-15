package uk.ac.aber.dcs.cs31620.healthypaws.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CalendarEventDao {
    @Insert
    suspend fun insertEvent(event: CalendarEvent)

    @Insert
    suspend fun insertMultipleEvents(eventsList: List<CalendarEvent>)

    @Update
    suspend fun updateEvent(event: CalendarEvent)

    @Delete
    suspend fun deleteEvent(event: CalendarEvent)

    @Query("DELETE FROM events")
    suspend fun deleteAll()

    @Query("SELECT * FROM events")
    fun getAllEvents(): LiveData<List<CalendarEvent>>
}