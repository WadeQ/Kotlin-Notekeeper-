package com.wadektech.keeper.ui

import com.wadektech.keeper.models.Note
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions

internal class AddNoteActivityTest {

    @Test
    fun saveNotesToDB() {
        val title = "Title"
        val body = "This is a note for testing our note function."
        val note = Note(title,body)
        Assertions.assertEquals(title, note.title)
        Assertions.assertEquals(body, note.note)

    }
}