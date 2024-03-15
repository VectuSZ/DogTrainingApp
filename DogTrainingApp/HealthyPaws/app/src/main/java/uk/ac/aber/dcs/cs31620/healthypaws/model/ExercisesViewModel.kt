package uk.ac.aber.dcs.cs31620.healthypaws.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.healthypaws.datasource.HealthyPawsRepository

class ExercisesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: HealthyPawsRepository = HealthyPawsRepository(application)

    var exerciseList: LiveData<List<Exercise>> = repository.getAllExercises()
        private set

    val selectedExercise = MutableLiveData<Exercise>()

    private fun getExercises(){
        exerciseList = repository.getAllExercises()
    }

    fun insertExercise(newExercise: Exercise){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertExercise(newExercise)
        }
    }

    fun insertMultipleExercise(exerciseList: List<Exercise>) {
        viewModelScope.launch(Dispatchers.IO){
            repository.insertMultipleExercises(exerciseList)
        }
    }

    fun deleteExercise(existingExercise: Exercise){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteExercise(existingExercise)
        }
    }

    fun deleteAll(exerciseList: List<Exercise>){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllExercises(exerciseList)
        }
    }
}