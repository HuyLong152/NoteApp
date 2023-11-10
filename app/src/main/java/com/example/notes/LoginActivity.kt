package com.example.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notes.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        binding.txtRegis.setOnClickListener{
            var intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.btnLoggin.setOnClickListener{
            val uEmail = binding.edtEmail.text.toString()
            val uPass = binding.edtPass.text.toString()
            if(uEmail.isNotEmpty() && uPass.isNotEmpty()){
                logginf(uEmail,uPass)
            }else{
                Toast.makeText(this, "Data is not empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun logginf(uEmail: String, uPass: String) {
        auth.signInWithEmailAndPassword(uEmail,uPass)
            .addOnSuccessListener {
                var intent = Intent(this,CreateNoteActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener{
                Toast.makeText(this, "Email or Password incorrect!!", Toast.LENGTH_SHORT).show()
            }
    }
}