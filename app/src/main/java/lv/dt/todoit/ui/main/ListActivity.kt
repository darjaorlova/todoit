package lv.dt.todoit.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list.*
import lv.dt.todoit.R

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set layout for this activity
        setContentView(R.layout.activity_list)

        // Create the adapter that will handle recycler view data
        // and pass a lambda (block of code) that will be executed
        // when an item has been clicked
        val adapter = NoteListAdapter(this) { noteId ->
            openFormActivity(noteId)
        }

        // Set recycler view to show items as list
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter

        // Fetch all notes from the database and update data in the adapter.
        // `getAllNotes()` returns LiveData object that observes notes data in
        // database and calls Observer if anything changes. This way our list is
        // always up to date, even if we change items in form screen.
        ViewModelProviders.of(this).get(ListViewModel::class.java)
            .getAllNotes()
            .observe(this, Observer { notes ->
                notes?.let { adapter.noteList = notes }
            })

        // Set a listener for the add button that will open FormActivity when it's clicked
        add.setOnClickListener {
            openFormActivity(null)
        }
    }

    private fun openFormActivity(noteId: Long?) {
        val intent = Intent(this, FormActivity::class.java)
        noteId?.let { intent.putExtra("note_id", it) }
        startActivity(intent)
    }

}