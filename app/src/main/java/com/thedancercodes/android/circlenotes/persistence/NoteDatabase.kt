package com.thedancercodes.android.circlenotes.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thedancercodes.android.circlenotes.Note

@Database(entities = [Note::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun notesDao(): NoteDao
}