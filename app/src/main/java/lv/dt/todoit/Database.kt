package lv.dt.todoit

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import android.os.AsyncTask


/*
 * This is the main SQLite database access class. In this app we use Room framework for database.
 * More info: https://developer.android.com/topic/libraries/architecture/room.html
 */
@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun notesDao(): NotesDao

}

/*
 * This class describes "notes" table in our database.
 */
@Entity(tableName = "notes")
data class Note(
    @ColumnInfo(name = "uid") @PrimaryKey(autoGenerate = true) var uid: Long = 0,
    @ColumnInfo(name = "date") var date: String = "",
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "note") var note: String = ""
)

/*
 * This interface describes what operation we can do with "notes" database.
 * Query annotations contain proper SQL queries.
 * Insert, Update and Delete annotations are also just shortcuts for corresponding SQL queries.
 */
@Dao
interface NotesDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE uid = :noteId")
    fun getNote(noteId: Long): LiveData<Note>

    @Insert
    fun saveNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}

/*
 * Since database operations can take a long time to complete those operations
 * MUST be do in background so it does not freeze applications UI.
 * For this reason we are using very simple AsyncTasks.
 *
 * https://developer.android.com/reference/android/os/AsyncTask.html
 */

class SaveAsync : AsyncTask<Note, Unit, Unit>() {
    override fun doInBackground(vararg params: Note) {
        App.NOTES.saveNote(params[0])
    }
}

class UpdateAsync : AsyncTask<Note, Unit, Unit>() {
    override fun doInBackground(vararg params: Note) {
        App.NOTES.updateNote(params[0])
    }
}

class DeleteAsync : AsyncTask<Note, Unit, Unit>() {
    override fun doInBackground(vararg params: Note) {
        App.NOTES.deleteNote(params[0])
    }
}