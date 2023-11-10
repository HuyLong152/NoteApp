package com.example.notes

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notes.databinding.ActivityAddNoteBinding
import com.example.notes.databinding.ActivityCreateNoteBinding
import com.example.notes.formData.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddNoteBinding
    lateinit var auth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.iconBack.setOnClickListener{
            finish()
        }
        binding.iconSave.setOnClickListener{
            val noteTitle = binding.edtTitle.text.toString()
            val noteDes = binding.edtDes.text.toString()
            if(noteTitle.isNotEmpty() && noteDes.isNotEmpty()){
                addNote(noteTitle,noteDes)

            }else{
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addNote(noteTitle: String, noteDes: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Notes").child(auth.currentUser!!.uid)
//        databaseReference.setValue(databaseReference.push().key!!)
        var noteId = databaseReference.push().key!!
        val newNote = Note(noteId,noteTitle,noteDes)
        databaseReference.child(noteId).setValue(newNote)
            .addOnSuccessListener{
                Toast.makeText(this, "add note successful", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener{
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }

    }
}