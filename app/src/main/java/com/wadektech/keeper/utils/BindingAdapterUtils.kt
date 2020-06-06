package com.wadektech.keeper.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.wadektech.keeper.models.Note

@BindingAdapter("noteTitle")
fun TextView.setNoteTitle(note: Note?){
    note ?.let {
        text = note.title
    }
}

@BindingAdapter("noteBody")
fun TextView.setNoteBody(note: Note?){
    note ?.let {
        text = note.note
    }
}