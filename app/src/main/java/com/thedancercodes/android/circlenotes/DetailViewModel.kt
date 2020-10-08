package com.thedancercodes.android.circlenotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.thedancercodes.android.circlenotes.persistence.Repository

/**
 * The logic for the detail screen.
 */
class DetailViewModel(private val repository: Repository) : ViewModel() {

  fun saveNewItem(note: Note, name: String) {
    repository.saveNoteItem(note, name)
  }

  fun getNote(id: Int): LiveData<Note> {
    return repository.getNote(id)
  }
}