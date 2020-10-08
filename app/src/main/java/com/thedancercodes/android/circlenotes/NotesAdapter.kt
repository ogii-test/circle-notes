package com.thedancercodes.android.circlenotes

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.item_note.view.*


class NotesAdapter(lifecycleOwner: LifecycleOwner,
                       private val note: LiveData<List<Note>>,
                       private val onItemSelected: (Note) -> Unit) :
    RecyclerView.Adapter<NotesListViewHolder>() {

  init {
    note.observe(lifecycleOwner, Observer { notifyDataSetChanged() })
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesListViewHolder {
    return NotesListViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.item_note, parent, false), onItemSelected)
  }

  override fun getItemCount(): Int {
    return note.value?.size ?: 0
  }

  override fun onBindViewHolder(holder: NotesListViewHolder, position: Int) {
    note.value?.get(position)?.let { holder.bind(it) }
  }
}

class NotesListViewHolder(val view: View, val onItemSelected: (Note) -> Unit) :
    RecyclerView.ViewHolder(view) {

  private val noteItemAdapter = NoteItemAdapter()

  init {
    view.recyclerNoteItems.layoutManager = LinearLayoutManager(view.context)
    view.recyclerNoteItems.adapter = noteItemAdapter
  }

  fun bind(note: Note) {
    view.title.text = note.receiver
    view.setOnClickListener {
      onItemSelected(note)
    }
    noteItemAdapter.items.clear()
    noteItemAdapter.items.addAll(note.notes)
    noteItemAdapter.notifyDataSetChanged()
  }
}

class NoteItemAdapter : RecyclerView.Adapter<NotesViewHolder>() {

  val items: MutableList<String> = mutableListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
    return NotesViewHolder(TextView(parent.context))
  }

  override fun getItemCount(): Int {
    return items.size
  }

  override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
    holder.bind(items[position])
  }

}

class NotesViewHolder(val view: TextView) : RecyclerView.ViewHolder(view) {
  fun bind(wish: String) {
    view.text = wish
  }
}

