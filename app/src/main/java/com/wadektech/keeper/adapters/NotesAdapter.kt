package com.wadektech.keeper.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wadektech.keeper.R
import com.wadektech.keeper.models.Note
import com.wadektech.keeper.utils.NotesDiffUtil
import kotlinx.android.synthetic.main.notes_item_list.view.*

class NotesAdapter(private val singleItemClicked: OnSingleItemClicked) : PagedListAdapter<Note, NotesAdapter.NotesViewHolder>(NotesDiffUtil()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_item_list, parent, false)
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = getItem(position)
        if (note != null) {
            holder.bind(note, singleItemClicked)
        }

    }

    inner class NotesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val title : TextView = itemView.tv_note_title
        val note : TextView = itemView.tv_notes_body

        fun bind(noteItem: Note, itemClicked: OnSingleItemClicked){
            title.text = noteItem.title
            note.text = noteItem.note
            itemView.setOnClickListener {
                itemClicked.onSingleNoteItemClicked(adapterPosition)
            }
        }
    }
    interface OnSingleItemClicked{
        fun onSingleNoteItemClicked(position: Int)
    }
}