package com.wadektech.keeper.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.wadektech.keeper.R
import com.wadektech.keeper.adapters.NotesAdapter
import com.wadektech.keeper.datasource.NotesRepository
import com.wadektech.keeper.db.NoteRoomDatabase
import com.wadektech.keeper.utils.NotesViewModelFactory
import com.wadektech.keeper.viewmodels.NotesViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var notesAdapter: NotesAdapter ?= null
    private var mLayout : LinearLayoutManager ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ft : FloatingActionButton = findViewById(R.id.ft_btn)
        ft.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        recyclerView.setHasFixedSize(true)
        mLayout = LinearLayoutManager(this)
        recyclerView.layoutManager = mLayout
        recyclerView.adapter = notesAdapter

        val db = NoteRoomDatabase(this)
        val repo = NotesRepository(db)
        val factory = NotesViewModelFactory(repo)

        val notesViewModel : NotesViewModel = ViewModelProvider(this, factory).get(NotesViewModel::class.java)
        notesViewModel.getAllNotesFromDB().observe(this, Observer {
            notesAdapter?.submitList(it)
        })
    }
}
