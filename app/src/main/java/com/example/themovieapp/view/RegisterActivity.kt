package com.example.themovieapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.themovieapp.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setListeners()
    }

    private fun setListeners() {
        buttonCancel.setOnClickListener(this)
        buttonSignUp.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val id = view.id

        if ( id == R.id.buttonCancel ) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else if ( id == R.id.buttonSignUp ) {
            val editNameString = editName.text.toString()
            val editLastNameString = editLastName.text.toString()
            val editEmailString = editName.text.toString()
            val editPasswordString = editPassword.text.toString()

            if ( editNameString == "" && editLastNameString == "" && editEmailString == "" && editPasswordString == "" ) {
                Toast.makeText(this, getString(R.string.put_datas), Toast.LENGTH_SHORT).show()
            } else if ( editNameString == "" || editLastNameString == "" || editEmailString == "" || editPasswordString == "" ) {
                Toast.makeText(this, getString(R.string.put_datas), Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}