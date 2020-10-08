package com.thedancercodes.android.circlenotes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_note_detail.*
import kotlinx.android.synthetic.main.view_input_bottom_sheet.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class NotesDetailActivity : AppCompatActivity() {

  companion object {
    private const val EXTRA_NOTE = "EXTRA_NOTE"

    fun newIntent(note: Note, context: Context): Intent {
      return Intent(context, NotesDetailActivity::class.java).apply {
        putExtra(EXTRA_NOTE, note.id)
      }
    }
  }

  private val viewModel: DetailViewModel by viewModel()
  private val noteAdapter: NoteItemAdapter = NoteItemAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_note_detail)
    recyclerNotes.layoutManager = LinearLayoutManager(this)
    recyclerNotes.adapter = noteAdapter
    viewModel.getNote(intent.getIntExtra(EXTRA_NOTE, 0)).observe(this, Observer {
      render(it)
    })
  }

  private fun render(note: Note) {
    textViewTitle.text = note.receiver
    noteAdapter.items.clear()
    noteAdapter.items.addAll(note.notes)
    noteAdapter.notifyDataSetChanged()

    buttonAddList.setOnClickListener { showAddListInput(note) }
  }

  private fun showAddListInput(note: Note) {
    BottomSheetDialog(this).apply {
      val view = layoutInflater.inflate(R.layout.view_input_bottom_sheet, null)
      view.title.text = getString(R.string.title_add_note_detail)
      view.buttonSave.setOnClickListener {
        viewModel.saveNewItem(note, view.editTextInput.text.toString())
        this.dismiss()
      }
      setContentView(view)
      show()
    }
  }
}
