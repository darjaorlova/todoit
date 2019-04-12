package lv.dt.todoit.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import lv.dt.todoit.App
import lv.dt.todoit.Note

/*
 * ViewModel is used to store data in case activity is paused or recreated.
 *
 * https://developer.android.com/topic/libraries/architecture/viewmodel.html
 */
class ListViewModel : ViewModel() {

    private var data: LiveData<List<Note>>? = null

    fun getAllNotes(): LiveData<List<Note>> {
        if (data == null) {
            data = App.NOTES.getAllNotes()
        }
        return data!!
    }

    override fun onCleared() {
        data = null
    }
}