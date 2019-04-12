package lv.dt.todoit.ui.main

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_form.*
import lv.dt.todoit.App
import lv.dt.todoit.DeleteAsync
import lv.dt.todoit.Note
import lv.dt.todoit.R
import lv.dt.todoit.SaveAsync
import lv.dt.todoit.UpdateAsync
import java.util.*


class FormActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private var currentNote: Note? = null

    private var currentDate: String = "13.04.2019"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        val currentNoteId = intent?.getLongExtra("note_id", 0) ?: 0
        App.NOTES.getNote(currentNoteId).observe(this, Observer { nullableNote ->
            currentNote = nullableNote

            nullableNote?.let { notNullNote ->
                currentDate = notNullNote.date
                input_note.setText(notNullNote.note)
                input_title.setText(notNullNote.title)
            }

            date.setText(nullableNote?.date ?: currentDate)
        })

        save.setOnClickListener {
            currentNote
                ?.let { idea -> updateNote(idea) }
                ?: saveNote()
            finish()
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.form_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            currentNote?.let { note ->
                DeleteAsync().execute(note)
            }
            finish()
            return true
        } else if (item.itemId == android.R.id.home) {
            finish()
            return true
        } else if (item.itemId == R.id.action_date) {
            val calendar = Calendar.getInstance(TimeZone.getDefault())

            val dialog = DatePickerDialog(
                this,
                this@FormActivity,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // TODO CHALLENGE: Change the format of the displayed date
        val d = dayOfMonth.formatWithZero()
        val m = (month + 1).formatWithZero()
        currentDate = "$d.$m.$year"
        date.text = currentDate
    }

    private fun updateNote(note: Note) {
        val newNote = Note(
            uid = note.uid,
            date = currentDate,
            title = input_title.text.toString(),
            note = input_note.text.toString()
        )
        UpdateAsync().execute(newNote)
    }

    private fun saveNote() {
        val newNote = Note(
            date = currentDate,
            title = input_title.text.toString(),
            note = input_note.text.toString()
        )
        SaveAsync().execute(newNote)
    }
}