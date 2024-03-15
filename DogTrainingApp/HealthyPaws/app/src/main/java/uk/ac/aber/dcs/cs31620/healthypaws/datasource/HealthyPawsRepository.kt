package uk.ac.aber.dcs.cs31620.healthypaws.datasource

import android.app.Application
import uk.ac.aber.dcs.cs31620.healthypaws.model.*

class HealthyPawsRepository(application: Application) {
    private val dogDao = HealthyPawsRoomDatabase.getDatabase(application)!!.dogDao()
    private val exerciseDao = HealthyPawsRoomDatabase.getDatabase(application)!!.exerciseDao()
    private val dietDao = HealthyPawsRoomDatabase.getDatabase(application)!!.dietDao()
    private val calendarEventDao = HealthyPawsRoomDatabase.getDatabase(application)!!.calendarEventDao()

    suspend fun insertDog(dog: Dog){
        dogDao.insertSingleDog(dog)
    }

    suspend fun insertMultipleDogs(dogs: List<Dog>){
        dogDao.insertMultipleDogs(dogs)
    }

    suspend fun updateDog(dog: Dog){
        dogDao.updateDog(dog)
    }

    suspend fun deleteDog(dog: Dog){
        dogDao.deleteDog(dog)
    }

    suspend fun deleteAllDogs(dogs: List<Dog>){
        dogDao.deleteAll()
    }

    fun getAllDogs() = dogDao.getAllDogs()

    suspend fun insertExercise(exercise: Exercise){
        exerciseDao.insertExercise(exercise)
    }

    suspend fun insertMultipleExercises(exercises: List<Exercise>) {
        exerciseDao.insertMultipleExercises(exercises)
    }

    suspend fun deleteExercise(exercise: Exercise){
        exerciseDao.deleteExercise(exercise)
    }

    suspend fun deleteAllExercises(exercises: List<Exercise>){
        exerciseDao.deleteAll()
    }

    fun getAllExercises() = exerciseDao.getAllExercises()

    fun getExercise(difficulty: Difficulty) = exerciseDao.getExercises(difficulty)

    suspend fun insertDiet(diet: Diet){
        dietDao.insertDiet(diet)
    }

    suspend fun insertMultipleDiets(diets: List<Diet>){
        dietDao.insertMultipleDiets(diets)
    }

    suspend fun deleteDiet(diet: Diet){
        dietDao.deleteDiet(diet)
    }

    suspend fun deleteAllDiets(diets: List<Diet>){
        dietDao.deleteAll()
    }

    fun getAllDiets() = dietDao.getAllDiets()

    suspend fun insertEvent(event: CalendarEvent){
        calendarEventDao.insertEvent(event)
    }

    suspend fun insertMultipleEvents(eventList: List<CalendarEvent>){
        calendarEventDao.insertMultipleEvents(eventList)
    }

    suspend fun deleteEvent(event: CalendarEvent){
        calendarEventDao.deleteEvent(event)
    }

    suspend fun deleteAllEvents(eventList: List<CalendarEvent>){
        calendarEventDao.deleteAll()
    }

    fun getAllEvents() = calendarEventDao.getAllEvents()
}