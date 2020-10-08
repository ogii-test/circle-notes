package com.thedancercodes.android.circlenotes.app

import androidx.room.Room
import com.thedancercodes.android.circlenotes.MainViewModel
import com.thedancercodes.android.circlenotes.DetailViewModel
import com.thedancercodes.android.circlenotes.persistence.Repository
import com.thedancercodes.android.circlenotes.persistence.RepositoryImpl
import com.thedancercodes.android.circlenotes.persistence.NoteDatabase
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

/**
 *  This handles the dependency injection for the app, specifying how to create any dependencies.
 */

val appModule = module {

  single<Repository> { RepositoryImpl(get()) }

  single {
    Room.databaseBuilder(
        get(),
        NoteDatabase::class.java, "notes-database"
    )
        .allowMainThreadQueries()
        .build().notesDao()
  }

  viewModel { MainViewModel(get()) }

  viewModel { DetailViewModel(get()) }
}