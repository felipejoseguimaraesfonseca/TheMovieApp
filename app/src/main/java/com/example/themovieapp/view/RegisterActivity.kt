package com.example.themovieapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.themovieapp.R
import com.example.themovieapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: UserViewModel
    private var mUserId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        observe()

        setListeners()
    }

    override fun onClick(view: View) {
        val id = view.id

        if (id == R.id.buttonCancel) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else if (id == R.id.buttonSignUp) {
            val firstName = editFirstName.text.toString()
            val lastName = editLastName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            if (firstName == "" && lastName == "" && email == "" && password == "") {
                Toast.makeText(this, getString(R.string.put_datas), Toast.LENGTH_SHORT).show()
            } else if (firstName == "" || lastName == "" || email == "" || password == "") {
                Toast.makeText(this, getString(R.string.put_datas), Toast.LENGTH_SHORT).show()
            } else {
                mViewModel.save(mUserId, firstName, lastName, email, password)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun observe() {
        mViewModel.saveUser.observe(this, Observer {
            if (it) {
                Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Falha", Toast.LENGTH_SHORT).show()
            }
            finish()
        })
    }

    private fun setListeners() {
        buttonCancel.setOnClickListener(this)
        buttonSignUp.setOnClickListener(this)
    }
}