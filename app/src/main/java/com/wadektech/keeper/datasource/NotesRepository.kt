package com.wadektech.keeper.datasource

import androidx.lifecycle.LiveData
import com.wadektech.keeper.db.NoteDao
import com.wadektech.keeper.db.NoteRoomDatabase
import com.wadektech.keeper.models.Note

class NotesRepository(private val roomDatabase: NoteRoomDatabase) {

    fun getAllNotesFromRoom():LiveData<List<Note>>{
        return roomDatabase.noteDao().getAllNotes()
    }

    suspend fun insertNotes(note: Note){
        roomDatabase.noteDao().saveNote(note)
    }
    suspend fun deleteNotes(note: Note){
        roomDatabase.noteDao().deleteNotes(note)
    }
    suspend fun updateNote(note: Note){
        roomDatabase.noteDao().updateNote(note)
    }
}