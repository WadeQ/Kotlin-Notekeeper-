package com.wadektech.keeper.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wadektech.keeper.R
import com.wadektech.keeper.models.Note
import com.wadektech.keeper.viewmodels.NotesViewModel
import kotlinx.android.synthetic.main.activity_add_note.*


class AddNoteActivity : AppCompatActivity() {
    private var notesViewModel: NotesViewModel?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        btn_save_note.setOnClickListener {
            saveNotesToDB()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveNotesToDB(){
        val title = et_title.toString().trim()
        val note = et_note_body.toString().trim()
        val notes = Note(title, note)
        notesViewModel?.insertNotesToDB(notes)
    }
}
