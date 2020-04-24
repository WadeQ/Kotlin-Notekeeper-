package com.wadektech.keeper.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wadektech.keeper.datasource.NotesRepository
import com.wadektech.keeper.db.NoteRoomDatabase
import com.wadektech.keeper.models.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NotesRepository): ViewModel() {

    fun getAllNotesFromDB() : LiveData<List<Note>> {
        return repository.getAllNotesFromRoom()
    }
    fun insertNotesToDB(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNotes(note)
    }
    fun updateNotes(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repository.updateNote(note)
    }
    fun deleteNotes(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNotes(note)
    }
}