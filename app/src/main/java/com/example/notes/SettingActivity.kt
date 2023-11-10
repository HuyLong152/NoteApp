package com.example.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notes.databinding.ActivitySettingBinding
import com.example.notes.formData.user
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SettingActivity : AppCompatActivity() {
    lateinit var binding:ActivitySettingBinding
    lateinit var auth:FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("usersAccount").child(auth.currentUser!!.uid)
        databaseReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val user = snapshot.getValue(user::class.java)
                    binding.txtName.text = user!!.userName.toString()
                    binding.txtEmail.text = user!!.userEmail.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        binding.btnBack.setOnClickListener{
            finish()
        }
        binding.btnLogout.setOnClickListener{
            auth.signOut()
            var itent = Intent(this, LoginActivity::class.java)
            startActivity(itent)
            finish()
        }
    }

}