package com.wadektech.keeper.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wadektech.keeper.R
import com.wadektech.keeper.models.Note
import com.wadektech.keeper.utils.NotesDiffUtil
import kotlinx.android.synthetic.main.notes_item_list.view.*

class NotesAdapter(val notesList: List<Note>) : ListAdapter<Note, NotesAdapter.NotesViewHolder>(NotesDiffUtil()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_item_list, parent, false)
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val pos = notesList[position]
        holder.title.text = pos.title
        holder.note.text = pos.note
    }
    class NotesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val title : TextView = itemView.tv_note_title
        val note : TextView = itemView.tv_notes_body
    }
}