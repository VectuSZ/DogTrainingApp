package uk.ac.aber.dcs.cs31620.healthypaws.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.healthypaws.datasource.HealthyPawsRepository

class DogViewModel(application: Application) : AndroidViewModel(application){
    private val repository: HealthyPawsRepository = HealthyPawsRepository(application)

    var dogList: LiveData<List<Dog>> = repository.getAllDogs()
        private set

    val selectedDog = MutableLiveData<Dog>()

    var numOfDog = 0

    var currentListOfKnownCommands: MutableList<Exercise> = mutableListOf()


    private fun getDogs(){
        dogList = repository.getAllDogs()
    }


    fun insertDog(newDog: Dog){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertDog(newDog)
        }
    }

    fun insertMultipleDogs(dogList: List<Dog>){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertMultipleDogs(dogList)
        }
    }

    fun deleteDog(existingDog: Dog){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteDog(existingDog)
        }
    }

    fun deleteAll(dogList: List<Dog>){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllDogs(dogList)
        }
    }


}