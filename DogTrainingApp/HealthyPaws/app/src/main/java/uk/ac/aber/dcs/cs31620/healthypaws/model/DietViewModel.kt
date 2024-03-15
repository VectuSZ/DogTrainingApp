package uk.ac.aber.dcs.cs31620.healthypaws.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.healthypaws.datasource.HealthyPawsRepository

class DietViewModel(application: Application) : AndroidViewModel(application){
    private val repository: HealthyPawsRepository = HealthyPawsRepository(application)

    var dietList: LiveData<List<Diet>> = repository.getAllDiets()
        private set

    private fun getDiets(){
        dietList = repository.getAllDiets()
    }

    fun insertDiet(newDiet: Diet){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertDiet(newDiet)
        }
    }

    fun insertMultipleDiets(dietsList: List<Diet>){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertMultipleDiets(dietsList)
        }
    }

    fun deleteDiet(existingDiet: Diet){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteDiet(existingDiet)
        }
    }

    fun deleteAll(dietList: List<Diet>){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllDiets(dietList)
        }
    }
}
