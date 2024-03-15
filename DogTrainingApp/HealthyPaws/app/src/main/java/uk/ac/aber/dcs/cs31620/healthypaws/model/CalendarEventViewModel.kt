package uk.ac.aber.dcs.cs31620.healthypaws.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.healthypaws.datasource.HealthyPawsRepository
import java.time.LocalDate
import java.time.LocalTime

class CalendarEventViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: HealthyPawsRepository = HealthyPawsRepository(application)

    var eventList: LiveData<List<CalendarEvent>> = repository.getAllEvents()
        private set

    private val _selectedEventTitle = MutableLiveData<String>()

    private val _selectedEventTime = MutableLiveData<LocalTime>()

    private val selectedDate = MutableLiveData<LocalDate>()

    private val selectedTime = MutableLiveData<LocalTime>()

    val selectedEventTitle: LiveData<String>
        get() = _selectedEventTitle

    val selectedEventTime : LiveData<LocalTime>
        get() = _selectedEventTime

    private fun getEvents(){
        eventList = repository.getAllEvents()
    }

    fun setSelectedDate(date: LocalDate){
        selectedDate.value = date
        val events = getEventsForDate(date)
        if (events.isNotEmpty()) {
            setSelectedEventTitle(events.first().title)
        } else {
            setSelectedEventTitle("")
        }
    }

    fun setSelectedTime(time: LocalTime) {
        selectedTime.value = time
        val events = getEventsForTime(time)
        if (events.isNotEmpty()) {
            setSelectedEventTitle(events.first().title)
        } else {
            setSelectedEventTitle("")
        }
    }

    fun getEventsForDate(date: LocalDate): List<CalendarEvent> {
        val events = eventList.value ?: emptyList()
        return events.filter { it.date == date }
    }

    fun getEventsForTime(time: LocalTime): List<CalendarEvent> {
        val events = eventList.value ?: emptyList()
        return events.filter { it.time == time }
    }

    fun setSelectedEventTime(time: LocalTime){
        _selectedEventTime.value = time
    }

    fun setSelectedEventTitle(title: String){
        _selectedEventTitle.value = title
    }

    fun insertEvent(newEvent: CalendarEvent){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertEvent(newEvent)
        }
    }

    fun insertMultipleEvents(eventList: List<CalendarEvent>){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertMultipleEvents(eventList)
        }
    }

    fun deleteEvent(existingEvent: CalendarEvent){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteEvent(existingEvent)
        }
    }

    fun deleteAll(eventList: List<CalendarEvent>){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllEvents(eventList)
        }
    }
}