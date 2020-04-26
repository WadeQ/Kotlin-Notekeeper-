package com.wadektech.keeper

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.wadektech.keeper.db.NoteDao
import com.wadektech.keeper.db.NoteRoomDatabase
import com.wadektech.keeper.models.Note
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4ClassRunner::class)
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
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    suspend fun testInsertedAndRetrievedNotesMatch() {
        val note = Note("Title", "Body of the note")
        noteDao.saveNote(note)

        val notesGet = noteDao.getAllNotes()
        Assert.assertEquals(note, notesGet)
    }
}