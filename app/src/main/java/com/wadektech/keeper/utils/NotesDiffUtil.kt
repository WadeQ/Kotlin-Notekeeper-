package com.wadektech.keeper.utils

import androidx.recyclerview.widget.DiffUtil
import com.wadektech.keeper.models.Note

class NotesDiffUtil : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
       return oldItem.id == newItem.id
    }
}