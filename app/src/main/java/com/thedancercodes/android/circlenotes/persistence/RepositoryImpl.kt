package com.thedancercodes.android.circlenotes.persistence

import androidx.lifecycle.LiveData
import com.thedancercodes.android.circlenotes.Note

/**
 * The repository that connects the app to the database.
 *
 * The implementation of the Repository interface.
 */
class RepositoryImpl(val noteDao: NoteDao) : Repository {

  override fun saveNote(note: Note) {
    noteDao.save(note)
  }

  override fun getNotes(): LiveData<List<Note>> {
      return noteDao.getAll()
  }

  override fun getNote(id: Int): LiveData<Note> {
    return noteDao.findById(id)
  }

  override fun saveNoteItem(
          note: Note,
          name: String
  ) {

    /*
     * NOTE: We are making a copy of the notes here instead of mutating it.
     * This is to follow the safety of immutability.
     *
     * You can learn more about this here:
     * https://medium.freecodecamp.org/functional-programming- for-android-developers-part-2-5c0834669d1a.
     */
    noteDao.save(
            note.copy(notes = note.notes + name)
    )
  }
}