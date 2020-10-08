package com.thedancercodes.android.circlenotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.thedancercodes.android.circlenotes.persistence.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {

  fun saveNewNotes(name: String) {
      repository.saveNote(Note(name, listOf()))
  }

  fun getNotes(): LiveData<List<Note>> {
    return repository.getNotes()
  }
}