package com.wadektech.keeper.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.wadektech.keeper.R
import com.wadektech.keeper.datasource.NotesRepository
import com.wadektech.keeper.db.NoteRoomDatabase
import com.wadektech.keeper.models.Note
import com.wadektech.keeper.utils.NotesViewModelFactory
import com.wadektech.keeper.utils.toast
import com.wadektech.keeper.viewmodels.NotesViewModel
import kotlinx.android.synthetic.main.activity_add_note.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class AddNoteActivity : AppCompatActivity(), KodeinAware {
    private lateinit var notesViewModel: NotesViewModel
    override val kodein by kodein()
     //private val factory : NotesViewModelFactory by instance<NotesViewModelFactory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        val db = NoteRoomDatabase(this)
        val repo = NotesRepository(db)
        val factory = NotesViewModelFactory(repo)

        notesViewModel = ViewModelProvider(this, factory).get(NotesViewModel::class.java)

        btn_save_note.setOnClickListener {
            saveNotesToDB()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveNotesToDB() {
        val title = et_title.text.toString().trim()
        val note = et_note_body.text.toString().trim()
        when {
            title.isEmpty() -> {
                et_title.error = "Title cannot be blank!"
                et_title.requestFocus()
                return
            }
            note.isEmpty() -> {
                et_note_body.error = "Body cannot be blank!"
                et_note_body.requestFocus()
                return
            }
            else -> {
                val notes = Note(title, note)
                Log.d("saveNotesToDB():", "{$notes} saved.")
                notesViewModel.insertNotesToDB(notes)
                this.toast("Notes saved...")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        notesViewModel.cancelJobs()
    }
}
