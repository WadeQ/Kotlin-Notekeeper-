package com.wadektech.keeper.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wadektech.keeper.datasource.NotesRepository
import com.wadektech.keeper.models.Note
import kotlinx.coroutines.*

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {

     var job : CompletableJob? = null

    fun getAllNotesFromDB(): LiveData<List<Note>> {
        return repository.getAllNotesFromRoom()
    }

    fun insertNotesToDB(note: Note) = viewModelScope.launch {
        repository.insertNotes(note)
    }

    fun updateNotes(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }

    fun deleteNotes(note: Note) = viewModelScope.launch {
        repository.deleteNotes(note)
    }

    fun getAllNotesFromRoom(): LiveData<Note>{
        job = Job()
        return object : LiveData<Note>(){
            override fun onActive() {
                super.onActive()
                job ?.let {
                    CoroutineScope(Dispatchers.IO + it).launch {
                       repository.getAllNotesFromRoom()
                        it.complete()
                    }
                }
            }
        }
    }

    fun cancelJobs(){
        job ?.cancel()
    }
}