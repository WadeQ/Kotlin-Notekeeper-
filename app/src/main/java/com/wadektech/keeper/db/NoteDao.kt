package com.wadektech.keeper.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wadektech.keeper.models.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNotes(note: Note)

    @Query("SELECT * FROM notes_db ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>


}