package com.wadektech.keeper

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.wadektech.keeper.db.NoteDao
import com.wadektech.keeper.db.NoteRoomDatabase
import com.wadektech.keeper.models.Note
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteRoomDatabaseTest {
    private lateinit var noteDao: NoteDao
    private lateinit var db: NoteRoomDatabase

    @Before
    fun createDB(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
            context,
            NoteRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        noteDao = db.noteDao()

    }

    @After
    @Throws(Exception::class)
    fun closeDB(){
        db.close()
    }

    @After
    @Throws(Exception::class)
    suspend fun insertAndGetNote(){
        val note = Note("Covid-19 Data","2 Million people worldwide have been infected so far.")
        noteDao.saveNote(note)
        val notes = noteDao.getAllNotes()

        assertEquals(note.id, notes.value)
        assertEquals(note.note, notes.value)
        assertEquals(note.title, notes.value)

    }
}