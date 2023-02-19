package com.example.v16

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignInActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference

    companion object {
        const val KEY1 = "com.example.v16.SignInActivity.KEY1"
        const val KEY2 = "com.example.v16.SignInActivity.KEY2"
        const val KEY3 = "com.example.v16.SignInActivity.KEY3"


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_sign_in)

        val signInButton = findViewById<Button>(R.id.btnsignIN)
        val userName = findViewById<TextInputEditText>(R.id.userNameEditText)

        signInButton.setOnClickListener {
            val uniqueId = userName.text.toString()
            if (uniqueId.isNotEmpty()) {
                readData(uniqueId)
            } else {
                Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readData(uniqueId: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

//        getting the value of child i.e. username or uniqueId form above reference

//        addonSuccessListener only tells that we have reached till database
        databaseReference.child(uniqueId).get().addOnSuccessListener {
//            if user exist or not
            if(it.exists()){

//              "it" means this uniqueId child

                val email = it.child("email").value
                val name = it.child("name").value
                val userId = it.child("uniqueId").value

                val intentWelcome = Intent(this,WelcomeActivity::class.java).apply {
                    putExtra(KEY1,email.toString())
                    putExtra(KEY2,name.toString())
                    putExtra(KEY3,userId.toString())
                }
                startActivity(intentWelcome)
            }
            else
            {
                Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener {
            Toast.makeText(applicationContext, "Fail Fetching", Toast.LENGTH_SHORT).show()
        }
    }
}