package com.example.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.notes.databinding.ActivityCreateNoteBinding
import com.example.notes.formData.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CreateNoteActivity : AppCompatActivity() {
    lateinit var binding:ActivityCreateNoteBinding
    lateinit var auth:FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    private val noteList = mutableListOf<Note>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.btnCreate.setOnClickListener{
            var itent = Intent(this,AddNoteActivity::class.java)
            startActivity(itent)
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("Notes").child(auth.currentUser!!.uid)
        val adapter = NoteAdapter(noteList) {
            var itent = Intent(this, DetailNoteActivity::class.java)
                .putExtra("noteId",it.id)
            startActivity(itent)
        }
        binding.recycleView.adapter = adapter
        databaseReference.addValueEventListener(
            object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    noteList.clear()
                    for (item in snapshot.children){
                        val note = item.getValue(Note::class.java)
                        noteList.add(note!!)
                    }
                    adapter.notifyDataSetChanged()
                    if(noteList.size == 0){
                        binding.noNote.visibility = View.VISIBLE
                        binding.recycleView.visibility = View.GONE
                    }else{
                        binding.noNote.visibility = View.GONE
                        binding.recycleView.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@CreateNoteActivity, "can't load data", Toast.LENGTH_SHORT).show()
                }

            }
        )
        binding.btnProfile.setOnClickListener {
            var itent = Intent(this, SettingActivity::class.java)
            startActivity(itent)
        }
    }
}