package com.example.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notes.databinding.ActivityStartNoteBinding
import com.google.firebase.auth.FirebaseAuth

class StartNote : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var binding:ActivityStartNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.button.setOnClickListener{
            var intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.txtHaveAcc.setOnClickListener{
            var intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        if(auth!!.currentUser != null){
            var intent = Intent(this,CreateNoteActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}