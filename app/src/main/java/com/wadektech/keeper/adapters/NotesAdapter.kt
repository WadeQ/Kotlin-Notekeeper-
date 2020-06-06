package com.wadektech.keeper.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wadektech.keeper.databinding.NotesItemListBinding
import com.wadektech.keeper.models.Note
import com.wadektech.keeper.utils.NotesDiffUtil

class NotesAdapter(private val singleItemClicked: OnSingleItemClicked) :
    PagedListAdapter<Note, NotesAdapter.NotesViewHolder>(NotesDiffUtil()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = getItem(position)
        if (note != null) {
            holder.bind(note, singleItemClicked)
        }
    }

    class NotesViewHolder private constructor(val binding: NotesItemListBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(noteItem: Note, itemClicked: OnSingleItemClicked){
            binding.tvNoteTitle.text = noteItem.title
            binding.tvNotesBody.text = noteItem.note
            itemView.setOnClickListener {
                itemClicked.onSingleNoteItemClicked(adapterPosition)
            }
        }
        companion object {
            fun from(parent: ViewGroup): NotesViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = NotesItemListBinding.inflate(inflater, parent, false)
                return NotesViewHolder(binding)
            }
        }
    }

    interface OnSingleItemClicked{
        fun onSingleNoteItemClicked(position: Int)
    }
}