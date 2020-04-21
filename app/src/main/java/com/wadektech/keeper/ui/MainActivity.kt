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
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance as instance1


class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory : NotesViewModelFactory by instance1()

    private var notesAdapter: NotesAdapter ?= null
    private var mLayout : LinearLayoutManager ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ft_btn.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

       initRecyclerview()

        val notesViewModel : NotesViewModel = ViewModelProvider(this, factory).get(NotesViewModel::class.java)
        notesViewModel.getAllNotesFromDB().observe(this, Observer {
            notesAdapter?.submitList(it)
        })
    }

    private fun initRecyclerview(){
        recyclerView.setHasFixedSize(true)
        mLayout = LinearLayoutManager(this)
        recyclerView.layoutManager = mLayout
        recyclerView.adapter = notesAdapter
    }
}
