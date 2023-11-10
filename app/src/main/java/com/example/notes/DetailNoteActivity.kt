package com.example.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import com.example.notes.databinding.ActivityDetailNoteBinding
import com.example.notes.formData.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DetailNoteActivity : AppCompatActivity() {
    lateinit var binding:ActivityDetailNoteBinding
    lateinit var auth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailNoteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContentView(binding.root)
        val noteId = intent.getStringExtra("noteId")
        databaseReference = FirebaseDatabase.getInstance().getReference("Notes").child(auth.currentUser!!.uid).child(noteId!!)

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val note:Note = snapshot.getValue(Note::class.java)!!
                    if(note != null){
                        binding!!.edtTitle.setText(note.title)
                        binding!!.edtDes.setText(note.description)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        binding.iconMore.setOnClickListener{view ->
            var popUpMenu = PopupMenu(this,view)
            val inflate = popUpMenu.menuInflater
            inflate.inflate(R.menu.popup_menu,popUpMenu.menu)
            popUpMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.save -> {
                        updateNote(noteId)
                        true
                    }
                    R.id.delete ->{
                        deleteNote()
                        true
                    }else -> false
                }
            }
                popUpMenu.show()
        }
        binding.iconBack.setOnClickListener{
            var itent = Intent(this, CreateNoteActivity::class.java)
            startActivity(itent)
        }
    }

    private fun deleteNote() {
        databaseReference.removeValue()
            .addOnSuccessListener(){
                Toast.makeText(this, "Delete successful", Toast.LENGTH_SHORT).show()
                var itent = Intent(this, CreateNoteActivity::class.java)
                startActivity(itent)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Delete Fail", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateNote(noteId: String) {
        val noteTitle = binding.edtTitle.text.toString()
        val noteDes = binding.edtDes.text.toString()
        if(noteTitle.isNotEmpty() && noteDes.isNotEmpty()){
            databaseReference.setValue(Note(noteId,noteTitle,noteDes))
                .addOnSuccessListener{
                    Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show()
                    var itent = Intent(this, CreateNoteActivity::class.java)
                    startActivity(itent)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Update Fail", Toast.LENGTH_SHORT).show()
                }

        }else{
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
        }
    }


}