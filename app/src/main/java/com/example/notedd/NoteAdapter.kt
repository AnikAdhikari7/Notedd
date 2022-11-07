package com.example.notedd

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter(
    private val notes: MutableList<Note>
) :RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_note,
                parent,
                false
            )
        )
    }

    fun addNote(note: Note) {
        notes.add(note)
        notifyItemInserted(notes.size - 1)
    }

    fun deleteNotes() {
        notes.removeAll { note ->
            note.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tvNoteTitle: TextView, isChecked: Boolean) {
        if (isChecked) {
            tvNoteTitle.paintFlags = tvNoteTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvNoteTitle.paintFlags = tvNoteTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val curNote = notes[position]
        holder.itemView.apply {
            tvNoteTitle.text = curNote.title
            cbDone.isChecked = curNote.isChecked

            toggleStrikeThrough(tvNoteTitle, curNote.isChecked)
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(tvNoteTitle, isChecked)
                curNote.isChecked = !curNote.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}