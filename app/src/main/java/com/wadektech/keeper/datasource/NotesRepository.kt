package com.wadektech.keeper.datasource

import androidx.lifecycle.LiveData
import com.wadektech.keeper.db.NoteDao
import com.wadektech.keeper.db.NoteRoomDatabase
import com.wadektech.keeper.models.Note

class NotesRepository(private val noteDao: NoteDao) {

    fun getAllNotesFromRoom():LiveData<List<Note>>{
        return noteDao.getAllNotes()
    }

    suspend fun insertNotes(note: Note){
        noteDao.saveNote(note)
    }
    suspend fun deleteNotes(note: Note){
        noteDao.deleteNotes(note)
    }
    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }

}