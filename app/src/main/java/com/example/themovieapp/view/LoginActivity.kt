package com.example.themovieapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.themovieapp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setListeners()
    }

    private fun setListeners() {
        buttonLogIn.setOnClickListener(this)
        textRegisterHere.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val id = view.id

        if (id == R.id.buttonLogIn) {
            val editEmailString = editEmail.text.toString()
            val editPasswordString = editPassword.text.toString()

            if (editEmailString == "" && editPasswordString == "") {
                Toast.makeText(this, getString(R.string.put_datas), Toast.LENGTH_SHORT).show()
            } else if ( editEmailString == "" || editPasswordString == "") {
                Toast.makeText(this, getString(R.string.put_datas), Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

        } else if (id == R.id.textRegisterHere) {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }
}