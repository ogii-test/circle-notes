package com.thedancercodes.android.circlenotes.persistence

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.thedancercodes.android.circlenotes.Note
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {

    /**
     * Android Architecture Components use an asynchronous background executor to do their work.
     * InstantTaskExecutorRule is a rule that swaps out that executor and replaces it with a
     * synchronous one.
     *
     * This will make sure that, when you're using LiveData, it's all run synchronously in the tests.
     */
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // properties to hold your NoteDatabase and NoteDao.
    private lateinit var noteDatabase: NoteDatabase
    private lateinit var noteDao: NoteDao

    // Initialize NoteDatabase and NoteDao using an in-memory database
    @Before
    fun initDb() {

        // Information stored in an in-memory database disappears when the tests finish,
        noteDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().context,
                NoteDatabase::class.java).build()

        noteDao = noteDatabase.notesDao()
    }

    // test that when there's nothing saved, getAll() returns an empty list.
    // This tests the result of a LiveData response
    // create a mock Observer, observe the LiveData returned from getAll() with it, and verify the result is an empty list.
    @Test
    fun getAllReturnsEmptyList() {
        val testObserver: Observer<List<Note>> = mock()
        noteDao.getAll().observeForever(testObserver)
        verify(testObserver).onChanged(emptyList())
    }

    // Testing an insert
    @Test
    fun saveNotesSavesData() {

        // Create notes and save them to the database.
        val note1 = NoteFactory.makeNote()
        val note2 = NoteFactory.makeNote()
        noteDao.save(note1, note2)

        // Use mock testObserver to call getAll().
        val testObserver: Observer<List<Note>> = mock()
        noteDao.getAll().observeForever(testObserver)

        // ArgumentCaptor to capture the value in onChanged().
        // Using an ArgumentCaptor from Mockito allows you to make more complex assertions on a
        // value than equals().
        val listClass =
                ArrayList::class.java as Class<ArrayList<Note>>
        val argumentCaptor = ArgumentCaptor.forClass(listClass)

        // Test that the result from the database is a non empty list.
        // At this point you care that data was saved and not what was
        //saved, so you're checking the list size only.
        verify(testObserver).onChanged(argumentCaptor.capture())

        assertTrue(argumentCaptor.value.size > 0)
    }

    // Testing your query
    @Test
    fun getAllRetrievesData() {
        val note1 = NoteFactory.makeNote()
        val note2 = NoteFactory.makeNote()
        noteDao.save(note1, note2)

        val testObserver: Observer<List<Note>> = mock()
        noteDao.getAll().observeForever(testObserver)

        val listClass =
                ArrayList::class.java as Class<ArrayList<Note>>
        val argumentCaptor = ArgumentCaptor.forClass(listClass)

        verify(testObserver).onChanged(argumentCaptor.capture())

        val capturedArgument = argumentCaptor.value

        // Test that the list result contains the exact notes you expect.
        assertTrue(capturedArgument
                .containsAll(listOf(note1, note2)))
    }

    // Testing an item query by id
    @Test
    fun findByIdRetrievesCorrectData() {

        // Create notes
        val note1 = NoteFactory.makeNote()
        val note2 = NoteFactory.makeNote()
        noteDao.save(note1, note2)

        val testObserver: Observer<Note> = mock()

        // Query a specific note and verify the result is correct
        noteDao.findById(note2.id).observeForever(testObserver)
        verify(testObserver).onChanged(note2)
    }



    // After the tests finish, close the database.
    @After
    fun closeDb() {
        noteDatabase.close()
    }

}