package com.wadektech.keeper.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wadektech.keeper.datasource.NotesRepository
import com.wadektech.keeper.viewmodels.NotesViewModel

@Suppress("UNCHECKED_CAST")
class NotesViewModelFactory(private val notesRepository: NotesRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NotesViewModel(notesRepository) as T
    }
}