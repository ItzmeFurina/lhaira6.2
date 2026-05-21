package com.example.zornosa_62_exer3.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.zornosa_62_exer3.data.Event
import com.example.zornosa_62_exer3.data.EventDatabase
import com.example.zornosa_62_exer3.data.EventRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EventViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: EventRepository
    val allEvents: StateFlow<List<Event>>

    init {
        val eventDao = EventDatabase.getDatabase(application).eventDao()
        repository = EventRepository(eventDao)
        allEvents = repository.allEvents.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun addEvent(title: String, description: String, date: String) {
        viewModelScope.launch {
            repository.insert(Event(title = title, description = description, date = date))
        }
    }

    fun toggleEventCompletion(event: Event) {
        viewModelScope.launch {
            repository.toggleCompletion(event)
        }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch {
            repository.delete(event)
        }
    }
}
