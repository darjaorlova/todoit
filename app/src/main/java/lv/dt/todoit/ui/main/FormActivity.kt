package lv.dt.todoit.ui.main

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import lv.dt.todoit.Note
import lv.dt.todoit.R
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

        /*
           TODO connect your layout with some actions:
            1) Retrieve id of current note from `intent`
            2) Fetch note from `App.NOTES` by observing changes
            3) Cache data in `currentNote` for future use
            4) If data is not empty(null) write existing values to inputs
                  and save current date to currentDate
            6) Add 'save' button click listener to either update or save
                  note to database and finish activity
            [Cheat 3]
         */
    }

    /*
        TODO implement delete button
         1) Override `onPrepareOptionsMenu(Menu)` method of `AppCompatActivity`
         2) Using `menuInflates` inflate `R.menu.form_menu`
         3) Don`t forget to add `return true` so that menu is displayed
         [Cheat 6]
   */

    /*
        TODO implement delete button click handling
         1) If `currentNote` is not null execute `DeleteAsync`
         2) Finish current activity
         3) Don't forget to `return true` to show that we handled the click
         [Cheat 6]
     */

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
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
        //date.text = currentDate TODO uncomment this after step 3
    }

    /*
        TODO write a function that:
         1) Takes text from inputs
         2) Creates new Note object
         3) Executes `SaveAsync` to save new note to database
         [Cheat 4]
   */
    private fun saveNote() {

    }


    /*
        TODO write a function that:
         1) Takes text from inputs
         2) Creates new Note object with same uid
         3) Executes `UpdateAsync` to save idea changes to database
         [Cheat 5]
    */
    private fun updateNote(note: Note) {

    }
}