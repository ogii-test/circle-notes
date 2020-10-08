package com.thedancercodes.android.circlenotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_input_bottom_sheet.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

  private val viewModel: MainViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView( R.layout.activity_main)
    recyclerView.layoutManager = GridLayoutManager(this, 2)
    recyclerView.adapter = NotesAdapter(this, viewModel.getNotes()) {
      startActivity(NotesDetailActivity.newIntent(it, this))
    }

    buttonAddNote.setOnClickListener { showAddNoteInput() }
  }

  private fun showAddNoteInput() {
    BottomSheetDialog(this).apply {
      val view = layoutInflater.inflate(R.layout.view_input_bottom_sheet, null)
      view.title.text = getString(R.string.title_add_note)
      view.buttonSave.setOnClickListener {
        viewModel.saveNewNotes(view.editTextInput.text.toString())
        this.dismiss()
      }
      setContentView(view)
      show()
    }
  }
}