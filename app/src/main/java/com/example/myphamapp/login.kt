package com.example.myphamapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myphamapp.Model.user
import com.example.myphamapp.databinding.ActivityLoginBinding
import com.google.firebase.database.*

class login : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbRef = FirebaseDatabase.getInstance().getReference("user")

        binding.login.setOnClickListener {
            loginn()

        }
    }

    private fun loginn() {
        val email = binding.email.text.toString()
        val pass = binding.pass.text.toString()
        val query = dbRef.orderByChild("email").equalTo(email)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(user::class.java)
                        if (user?.pass.equals(pass) && user?.email.equals(email)) {
                            val iduser = user?.id_user.toString()
                            val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
                            sharedPreferences.edit().putString("iduser", iduser).apply()
//                            isPasswordMatched = true
                            val intent = Intent(this@login, navbottom::class.java)
//                            startActivity(intent)
                            Toast.makeText(this@login, "Login success", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this@login, "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                } else {
                    val Id_user = dbRef.push().key!!
                    val user = user(Id_user, email, pass)
                    dbRef.child(Id_user).setValue(user).addOnCompleteListener {
                        Toast.makeText(this@login, "Thêm dữ liệu thành công", Toast.LENGTH_SHORT)
                            .show()
                        Toast.makeText(this@login, "Login sucess", Toast.LENGTH_SHORT).show()

                    }.addOnFailureListener { err ->
                        Toast.makeText(this@login, "ERR ${err.message}", Toast.LENGTH_SHORT).show()
                    }
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}