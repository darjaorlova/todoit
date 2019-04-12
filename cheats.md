# Cheats

### Cheat 1: Open FormActivity when “+” button was clicked in ListActivity
``` kotlin
private fun openFormActivity(noteId: Long?) {
    val intent = Intent(this, FormActivity::class.java)
    noteId?.let { intent.putExtra("note_id", it) }
    startActivity(intent)
}
```

### Cheat 2: Create layout for FormActivity. Call it activity_form.xml.
``` xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/save"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_margin="16dp"
        android:layout_marginEnd="16dp"
        android:layout_marginBottom="16dp"
        android:tint="@color/white"
        app:fabSize="normal"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:srcCompat="@drawable/ic_done" />

    <com.google.android.material.textfield.TextInputLayout
        android:id="@+id/layout_title"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        android:layout_marginTop="16dp"
        android:layout_marginEnd="16dp"
        android:hint="title"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/input_title"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:backgroundTint="#e2e2e2"
            android:fontFamily="sans-serif-smallcaps"
            android:singleLine="true"
            android:textColor="@color/primary"
            android:textSize="20sp"
            android:textStyle="bold"
            android:typeface="normal" />
    </com.google.android.material.textfield.TextInputLayout>

    <com.google.android.material.textfield.TextInputLayout
        android:id="@+id/layout_note"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        android:layout_marginTop="24dp"
        android:layout_marginEnd="16dp"
        android:hint="Details"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/layout_title">

        <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/input_note"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:backgroundTint="#e2e2e2"
            android:fontFamily="sans-serif-light"
            android:gravity="top"
            android:inputType="textMultiLine"
            android:singleLine="false"
            android:textColor="@color/primary"
            android:textSize="18sp" />
    </com.google.android.material.textfield.TextInputLayout>

    <TextView
        android:id="@+id/date"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_margin="16dp"
        android:layout_marginStart="16dp"
        android:layout_marginBottom="16dp"
        android:fontFamily="sans-serif-light"
        android:padding="2dp"
        android:textColor="@color/primary"
        android:textSize="18sp"
        android:textStyle="italic"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/layout_note" />

</androidx.constraintlayout.widget.ConstraintLayout>
```


### Cheat 3: Bind FormActivity actions to it’s layout.
``` kotlin
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
```


### Cheat 4: Write a function, that saves note to database.
``` kotlin
    private fun saveNote() {
        val newNote = Note(
            date = currentDate,
            title = input_title.text.toString(),
            note = input_note.text.toString()
        )
        SaveAsync().execute(newNote)
    }
```


### Cheat 5: Write a function, that updates the existing note in database.
``` kotlin
    private fun updateNote(note: Note) {
        val newNote = Note(
            uid = note.uid,
            date = currentDate,
            title = input_title.text.toString(),
            note = input_note.text.toString()
        )
        UpdateAsync().execute(newNote)
    }
```


### Cheat 6: Add a delete button and action to the menu.
``` kotlin
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.form_menu, menu)
        return true
    }

    else if (item.itemId == R.id.action_delete) {
            currentNote?.let { note ->
                DeleteAsync().execute(note)
            }
            finish()
            return true
    }
```