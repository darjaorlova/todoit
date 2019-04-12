package lv.dt.todoit.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.view_todo_list_item.view.*
import lv.dt.todoit.Note
import lv.dt.todoit.R
import kotlin.properties.Delegates.observable

/*
 * For more information on adapters you can read:
 * https://developer.android.com/guide/topics/ui/layout/recyclerview.html
 * https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.html
 */
class NoteListAdapter(
    private val context: Context,
    private val onClickCallback: (Long) -> Unit
) : androidx.recyclerview.widget.RecyclerView.Adapter<NoteViewHolder>() {

    // Code in a block is executed every time value of this property is changed.
    // More info: https://kotlinlang.org/docs/reference/delegated-properties.html#observable
    var noteList: List<Note> by observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder = LayoutInflater
        .from(context)
        .inflate(R.layout.view_todo_list_item, parent, false)
        .let { NoteViewHolder(it) }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(noteList[position], onClickCallback)
    }

    override fun getItemCount(): Int = noteList.size
}

class NoteViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
    fun bind(item: Note, onClick: (Long) -> Unit) {
        itemView.date.text = item.date
        itemView.title.text = item.title
        itemView.todo.text = item.note
        itemView.card.setOnClickListener { onClick(item.uid) }
    }
}