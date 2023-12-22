package com.example.zd3_2up

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent

import android.os.Bundle
import android.view.View
import android.widget.EditText

class SignInActivity : Activity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        email=findViewById(R.id.emailEditText)
        password=findViewById(R.id.passwordEditText)
    }
    fun Login(view: View){
        if (email.text.toString().isNotEmpty() && password.text.toString().isNotEmpty()) {
            val intent = Intent(this, ChannesActivity::class.java)
            startActivity(intent)
        } else {
            val alert= AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("У вас есть пустые поля")
                .setPositiveButton("Ок",null)
                .create()
                .show()
        }
    }
}