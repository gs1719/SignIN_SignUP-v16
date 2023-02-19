package com.example.v16


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.v16.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        

        binding.btnsignup.setOnClickListener {
            val name = binding.etName.text.toString()
            val mail = binding.etmail.text.toString()
            val uniqueId = binding.etUserName.text.toString()
            val password = binding.etpassword.text.toString()

            val user = User(name,mail,password,uniqueId)
            database = FirebaseDatabase.getInstance().getReference("Users")

            database.child(uniqueId).setValue(user).addOnSuccessListener {
                binding.etUserName.text?.clear()
                binding.etmail.text?.clear()
                binding.etName.text?.clear()
                binding.etpassword.text?.clear()

                Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvSignIN.setOnClickListener {
            val openSignInActivity = Intent(this,SignInActivity::class.java)
            startActivity(openSignInActivity)
        }
    }
}