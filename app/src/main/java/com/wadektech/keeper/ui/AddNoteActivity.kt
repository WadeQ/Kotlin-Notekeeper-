package com.wadektech.keeper.ui

import android.content.Intent
import android.net.IpPrefix
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.wadektech.keeper.R
import com.wadektech.keeper.databinding.ActivityAddNoteBinding
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
import timber.log.Timber

class AddNoteActivity : AppCompatActivity(), KodeinAware {
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var binding : ActivityAddNoteBinding
    override val kodein by kodein()
     //private val factory : NotesViewModelFactory by instance<NotesViewModelFactory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)

        val db = NoteRoomDatabase(this)
        val repo = NotesRepository(db)
        val factory = NotesViewModelFactory(repo)

        notesViewModel = ViewModelProvider(this, factory).get(NotesViewModel::class.java)

        binding.btnSaveNote.setOnClickListener {
            saveNotesToDB()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun saveNotesToDB() {
        val title = binding.etTitle.text.toString().trim()
        val note = binding.etNoteBody.text.toString().trim()
        when {
            title.isEmpty() -> {
                binding.etTitle.error = "Title cannot be blank!"
                binding.etTitle.requestFocus()
                return
            }
            note.isEmpty() -> {
               binding.etNoteBody.error = "Body cannot be blank!"
                binding.etNoteBody.requestFocus()
                return
            }
            else -> {
                val notes = Note(title, note)
                Timber.d("saveNotesToDB(): {$notes} saved.")
                notesViewModel.insertNotesToDB(notes)
                this.toast("Notes saved...")
            }
        }
    }
}
