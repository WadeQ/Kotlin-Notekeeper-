package com.wadektech.keeper.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.wadektech.keeper.datasource.NotesRepository
import com.wadektech.keeper.db.NoteRoomDatabase
import com.wadektech.keeper.models.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application): AndroidViewModel(application) {
    private var notesRepository : NotesRepository
    var allNotes : LiveData<List<Note>>

    init {
        val notesDao = NoteRoomDatabase.invoke(application).noteDao()
        notesRepository = NotesRepository(notesDao)
        allNotes = notesRepository.getAllNotesFromRoom()
    }

    fun insertNotesToDB(note: Note) = viewModelScope.launch(Dispatchers.IO){
        notesRepository.insertNotes(note)
    }
    fun updateNotes(note: Note) = viewModelScope.launch(Dispatchers.IO){
        notesRepository.updateNote(note)
    }
    fun deleteNotes(note: Note) = viewModelScope.launch(Dispatchers.IO){
        notesRepository.deleteNotes(note)
    }
}