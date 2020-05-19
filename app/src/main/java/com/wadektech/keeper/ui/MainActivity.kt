package com.wadektech.keeper.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.wadektech.keeper.R
import com.wadektech.keeper.adapters.NotesAdapter
import com.wadektech.keeper.datasource.NotesRepository
import com.wadektech.keeper.db.NoteRoomDatabase
import com.wadektech.keeper.utils.NotesViewModelFactory
import com.wadektech.keeper.utils.toast
import com.wadektech.keeper.viewmodels.NotesViewModel
import com.wadektech.keeper.workmanager.application.NotesApplication
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import timber.log.Timber


class MainActivity : AppCompatActivity(), KodeinAware , NotesAdapter.OnSingleItemClicked{
    override val kodein by kodein()
     //private val factory : NotesViewModelFactory by instance<NotesViewModelFactory>()

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

        notesAdapter = NotesAdapter(this)
        recyclerView.adapter = notesAdapter

         val db = NoteRoomDatabase(this)
         val repo = NotesRepository(db)
         val factory = NotesViewModelFactory(repo)

        val notesViewModel = ViewModelProvider(this, factory).get(NotesViewModel::class.java)
        notesViewModel.getAllNotesFromDB().observe(this, Observer {
           Timber.d("getAllNotesFromDB(): {${it.size}}")
            notesAdapter.submitList(it)
        })
    }

    private fun initRecyclerview(){
        recyclerView.setHasFixedSize(true)
        mLayout = LinearLayoutManager(this)
        recyclerView.layoutManager = mLayout
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return super.onOptionsItemSelected(item)

    }

    override fun onSingleNoteItemClicked(position: Int) {
        toast("Item $position has been clicked.")
        Timber.d("onSingleNoteItemClicked(): the item at position : {$position} has been clicked")
    }

}
