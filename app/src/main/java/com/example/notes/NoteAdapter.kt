package com.example.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.formData.Note

class NoteAdapter(
    val list:List<Note>,
    val onItemClick:(Note)->Unit
):RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    class NoteViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val title : TextView=itemView.findViewById(R.id.txtTitle)
        val desc : TextView=itemView.findViewById(R.id.txtDes)
        val item:LinearLayout = itemView.findViewById(R.id.itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = list[position]
        holder.apply {
            title.text = note.title
            desc.text = note.description
            item.setOnClickListener{
                onItemClick(note)
            }
        }
    }

}