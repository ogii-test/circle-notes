package com.thedancercodes.android.circlenotes.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.thedancercodes.android.circlenotes.Note

/**
 * The Database Access Object.
 * You will work on defining the database interactions in this class.
 */
@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): LiveData<List<Note>>

    // OnConflictStrategy.REPLACE allows the the database to override an entry that already exists.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg notes: Note)

    @Query("SELECT * FROM note WHERE id = :id")
    fun findById(id: Int): LiveData<Note>
}