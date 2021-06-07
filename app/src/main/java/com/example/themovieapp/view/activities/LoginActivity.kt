package com.example.themovieapp.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.themovieapp.R
import com.example.themovieapp.databinding.ActivityLoginBinding
import com.example.themovieapp.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: UserViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mViewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UserViewModel::class.java)

        observe()

        setListeners()
    }

    override fun onClick(view: View) {
        val id = view.id

        if (id == R.id.buttonLogIn) {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if (email == "" && password == "") {
                Toast.makeText(
                    this,
                    getString(R.string.put_datas),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (email == "" || password == "") {
                Toast.makeText(
                    this,
                    getString(R.string.put_datas),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (email != "" && password != "") {
                if (this::mViewModel.isInitialized) {
                    val login = mViewModel.login(email, password)
                    if (login.toString() != "") {
                        val intent = Intent(this, NavigationActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            getString(R.string.datas_not_found),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        } else if (id == R.id.textRegisterHere) {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

   private fun observe() {
        this.mViewModel.user.observe( this, {
            if (it == null) {
                Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "Falha", Toast.LENGTH_LONG).show()
            }
            finish()
        })
    }

    private fun setListeners() {
        binding.buttonLogIn.setOnClickListener(this)
        binding.textRegisterHere.setOnClickListener(this)
    }
}