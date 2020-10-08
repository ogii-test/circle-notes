package com.thedancercodes.android.circlenotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.thedancercodes.android.circlenotes.persistence.NoteDao
import com.thedancercodes.android.circlenotes.persistence.NoteDatabase
import com.thedancercodes.android.circlenotes.persistence.RepositoryImpl
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class DetailViewModelTest {

    // TestRule that makes sure that when you're using LiveData with the ViewModel
    // it's all run synchronously in the tests.
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // A spy of NoteDao to use to create the Repository dependency for the DetailViewModel
    private val noteDao: NoteDao = Mockito.spy(
            Room.inMemoryDatabaseBuilder(
                    InstrumentationRegistry.getInstrumentation().context,
                    NoteDatabase::class.java)
                    .allowMainThreadQueries()
                    .build().notesDao())

    // A DetailViewModel, with a RepositoryImpl created from your spy.
    private val viewModel = DetailViewModel(
            RepositoryImpl(noteDao)
    )

    // Test verifying that saving a new item calls the database using the Data Access Object (DAO)
    @Test
    fun saveNewItemCallsDatabase() {

        // Call the saveNewItem() function with data.
        viewModel.saveNewItem(Note("Wambui",
                listOf("Mt. Kenya Hike", "Marsabit Road trip"), 1
        ), "Wambui vacation")

        // Verify that saveNewItem() called the save() function on the DAO.
        verify(noteDao).save(any())
    }

    // test that makes sure saveNewItem() saves the correct data.
    @Test
    fun saveNewItemSavesData() {

        // Create a new note.
        val note = Note("Raymond",
                listOf("Mt. Kenya Hike", "Marsabit Road trip"), 1)

        // Create a new item name for the list and call saveNewItem().
        val name = "Wambui vacation"
        viewModel.saveNewItem(note, name)

        // Create a mock Observer
        val mockObserver = mock<Observer<Note>>()

        /*
         * Query the database and ensure that the note you just saved returns,
         * signaling it saved correctly.
         *
         * When the program posts a value to a LiveData, the object calls onChanged() with the value.
         * This is the function you are checking for.
         */
        noteDao.findById(note.id)
                .observeForever(mockObserver)
        verify(mockObserver).onChanged(
                note.copy(notes = note.notes + name)
        )
    }

    // Testing DB queries
    // Test that getNote() calls the database using the DAO.
    @Test
    fun getNoteCallsDatabase() {
        viewModel.getNote(1)

        verify(noteDao).findById(any())
    }


    // Testing the data returned
    // Test that ensures correct data returns when calling getNote().
    @Test
    fun getNoteReturnsCorrectData() {

        // Set up your test data.
        val note = Note("Richard",
                listOf("Mt. Kenya Hike", "Marsabit Road trip"), 1)

        // Save a note to the database to be retrieved later in this test.
        noteDao.save(note)

        // Create a mockObserver, mocking a lifecycle Observer.
        // You use this observer to observeForever() on the LiveData that getNote() returns.
        val mockObserver = mock<Observer<Note>>()
        viewModel.getNote(1).observeForever(mockObserver)

        // Verify that the function published the correct data.
        verify(mockObserver).onChanged(note)
    }
}