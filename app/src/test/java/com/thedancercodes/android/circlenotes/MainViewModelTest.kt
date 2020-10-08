package com.thedancercodes.android.circlenotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.thedancercodes.android.circlenotes.persistence.Repository
import org.junit.Rule
import org.junit.Test

/**
 * How to test a ViewModel as a Unit test
 */
class MainViewModelTest {

    @get:Rule
    @Suppress("unused")
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockRepository: Repository = mock()

    private val viewModel = MainViewModel(mockRepository)

    @Test
    fun saveNewNotesCallsRepository() {
        viewModel.saveNewNotes("New list")

        verify(mockRepository).saveNote(any())
    }

    @Test
    fun saveNewNotesCallsRepositoryCorrectWithData() {
        val name = "New list"
        viewModel.saveNewNotes(name)

        verify(mockRepository).saveNote(Note(name, listOf()))
    }

    @Test
    fun getNotesCallsRepository() {
        viewModel.getNotes()

        verify(mockRepository).getNotes()
    }

    @Test
    fun getNotesReturnsReturnsData() {
        val notes = listOf(Note("Wambui Trip", listOf("Seychelles vacation")))
        whenever(mockRepository.getNotes())
                .thenReturn(MutableLiveData<List<Note>>().apply { postValue(notes) })

        val mockObserver = mock<Observer<List<Note>>>()
        viewModel.getNotes().observeForever(mockObserver)

        verify(mockObserver).onChanged(notes)
    }
}