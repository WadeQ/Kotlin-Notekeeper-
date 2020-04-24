package com.wadektech.keeper.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wadektech.keeper.R
import com.wadektech.keeper.adapters.NotesAdapter
import com.wadektech.keeper.datasource.NotesRepository
import com.wadektech.keeper.db.NoteRoomDatabase
import com.wadektech.keeper.utils.NotesViewModelFactory
import com.wadektech.keeper.viewmodels.NotesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein


class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private lateinit var notesAdapter: NotesAdapter
    private var mLayout : LinearLayoutManager ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ft_btn.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        initRecyclerview()

        notesAdapter = NotesAdapter()
        recyclerView.adapter = notesAdapter

        val db = NoteRoomDatabase(this)
        val repo = NotesRepository(db)
        val factory = NotesViewModelFactory(repo)

        val notesViewModel : NotesViewModel = ViewModelProvider(this, factory).get(NotesViewModel::class.java)
        notesViewModel.getAllNotesFromDB().observe(this, Observer {
            notesAdapter.submitList(it)
        })
    }

    private fun initRecyclerview(){
        recyclerView.setHasFixedSize(true)
        mLayout = LinearLayoutManager(this)
        recyclerView.layoutManager = mLayout
    }
}
