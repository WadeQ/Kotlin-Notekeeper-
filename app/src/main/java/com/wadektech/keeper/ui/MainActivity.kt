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
import com.wadektech.keeper.viewmodels.NotesViewModel


class MainActivity : AppCompatActivity() {
    private var mRecyclerView: RecyclerView  ?= null
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

        mRecyclerView = findViewById(R.id.recyclerView)
        mRecyclerView?.setHasFixedSize(true)
        mLayout = LinearLayoutManager(this)
        mRecyclerView?.layoutManager = mLayout
        mRecyclerView?.adapter = notesAdapter

        val notesViewModel : NotesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        notesViewModel.allNotes.observe(this, Observer {
            notesAdapter ?.submitList(it)
        })
    }

}
