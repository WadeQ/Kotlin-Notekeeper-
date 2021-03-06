package com.wadektech.keeper.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.wadektech.keeper.R
import com.wadektech.keeper.adapters.NotesAdapter
import com.wadektech.keeper.databinding.ActivityMainBinding
import com.wadektech.keeper.datasource.NotesRepository
import com.wadektech.keeper.db.NoteRoomDatabase
import com.wadektech.keeper.models.Note
import com.wadektech.keeper.utils.NotesViewModelFactory
import com.wadektech.keeper.utils.snackbar
import com.wadektech.keeper.utils.toast
import com.wadektech.keeper.viewmodels.NotesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import timber.log.Timber


class MainActivity : AppCompatActivity(), KodeinAware , NotesAdapter.OnSingleItemClicked {
    override val kodein by kodein()
    //private val factory : NotesViewModelFactory by instance<NotesViewModelFactory>()
    private lateinit var notesList : List<Note>
    private lateinit var notesAdapter: NotesAdapter
    private var mLayout: LinearLayoutManager? = null
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var binding: ActivityMainBinding
    companion object{
        const val EXTRA_NOTE_ID = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.ftBtn.setOnClickListener {
            val fabIntent = Intent(this, AddNoteActivity::class.java)
            startActivity(fabIntent)
        }

        initRecyclerview()

        notesList = ArrayList<Note>()

        notesAdapter = NotesAdapter(this)
        recyclerView.adapter = notesAdapter

        val db = NoteRoomDatabase(this)
        val repo = NotesRepository(db)
        val factory = NotesViewModelFactory(repo)

        notesViewModel = ViewModelProvider(this, factory).get(NotesViewModel::class.java)
        notesViewModel.getAllNotesFromDB().observe(this, Observer {
            Timber.d("getAllNotesFromDB(): {${it.size}}")
            notesAdapter.submitList(it)
        })

        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    //get position of note
                    val pos : Int = viewHolder.adapterPosition
                    //get list of notes and implement delete
                    notesAdapter.currentList?.get(pos)?.let {
                        notesViewModel.deleteNotes(it)
                        snackbar(main_activity, "Note deleted...")
                }
                }
            }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun initRecyclerview() {
        binding.recyclerView.setHasFixedSize(true)
        mLayout = LinearLayoutManager(this)
        recyclerView.layoutManager = mLayout
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_search -> searchInit()
            R.id.menu_settings -> initSettings()
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onSingleNoteItemClicked(position: Int) {
        val intent = Intent(applicationContext, AddNoteActivity::class.java)
        intent.putExtra(EXTRA_NOTE_ID, position)
        startActivity(intent)
        Timber.d("onSingleNoteItemClicked(): the item at position : {$position} has been clicked")
    }

    private fun initSettings(){
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun searchInit(){
        toast("Search implementation TODO")
    }
}

