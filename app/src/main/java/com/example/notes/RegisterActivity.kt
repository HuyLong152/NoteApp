package com.example.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notes.databinding.ActivityRegisterBinding
import com.example.notes.formData.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var dataReferent:DatabaseReference
    private lateinit var binding:ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.btnCreate.setOnClickListener{
            val uName = binding.edtName.text.toString()
            val uEmail = binding.edtEmail.text.toString()
            val uPass = binding.edtPass.text.toString()
            if(uName.isNotEmpty()  && uEmail.isNotEmpty() && uPass.isNotEmpty()){
                createAccount(uName,uEmail,uPass)
            }else{
                Toast.makeText(this, "Data is not empty", Toast.LENGTH_SHORT).show()
            }
        }
        binding.txtHaveAcc.setOnClickListener{
            var intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createAccount(uName: String, uEmail: String, uPass: String) {
        auth.createUserWithEmailAndPassword(uEmail,uPass)
            .addOnCompleteListener{task ->
                if(task.isSuccessful){
                    var currentUser = auth.currentUser
                    dataReferent = FirebaseDatabase.getInstance().getReference("usersAccount").child(currentUser!!.uid)
                    var newUser = user(uName,uEmail,"")
                    dataReferent.setValue(newUser)
                        .addOnSuccessListener {
                            auth.signOut()
                            var intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)

                        }
                        .addOnFailureListener{
                            Toast.makeText(this, "Can't add user !!!", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener{
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
    }

}