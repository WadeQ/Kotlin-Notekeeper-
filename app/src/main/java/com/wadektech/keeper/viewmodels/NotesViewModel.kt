package com.wadektech.keeper.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.wadektech.keeper.datasource.NotesRepository
import com.wadektech.keeper.models.Note
import kotlinx.coroutines.*

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {

        var job : CompletableJob? = null
        private var pagedList: LiveData<PagedList<Note>>

    init {
        val factory : DataSource.Factory<Int, Note> = repository.getAllNotesFromRoom()
        val pagedListBuilder: LivePagedListBuilder<Int, Note> = LivePagedListBuilder<Int, Note>(factory,
            7)
        pagedList = pagedListBuilder.build()
    }
    fun getAllNotesFromDB() = pagedList

    fun insertNotesToDB(note: Note) = viewModelScope.launch {
        repository.insertNotes(note)
    }

    fun updateNotes(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }

    fun deleteNotes(note: Note) = viewModelScope.launch {
        repository.deleteNotes(note)
    }

    fun getAllNotesFromRoom(): LiveData<Note>{
        job = Job()
        return object : LiveData<Note>(){
            override fun onActive() {
                super.onActive()
                job ?.let {
                    CoroutineScope(Dispatchers.IO + it).launch {
                       repository.getAllNotesFromRoom()
                        it.complete()
                    }
                }
            }
        }
    }

    fun cancelJobs(){
        job ?.cancel()
    }
}