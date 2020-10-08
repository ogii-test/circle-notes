package com.thedancercodes.android.circlenotes.persistence

import androidx.lifecycle.LiveData
import com.thedancercodes.android.circlenotes.Note

/**
 * The interface for the data repository in the app.
 */
interface Repository {
  fun saveNote(note: Note)
  fun getNotes(): LiveData<List<Note>>
  fun getNote(id: Int): LiveData<Note>
  fun saveNoteItem(note: Note, name: String)
}