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

        mViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UserViewModel::class.java)

        observe()

        setListeners()
    }

    override fun onClick(view: View) {
        val id = view.id

        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

        if (id == R.id.buttonLogIn) {
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
            } else {
                val observer = observe()

                mViewModel.login(email, password)

                if (observer.toString().toInt() == R.string.account_logged_successfully) {
                    val intent = Intent(this, NavigationActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        } else if (id == R.id.textRegisterHere) {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun observe() {
        mViewModel.messageEventData.observe(this, { stringResId ->
            Toast.makeText(this, stringResId, Toast.LENGTH_SHORT).show()
        })
    }


    private fun setListeners() {
        binding.buttonLogIn.setOnClickListener(this)
        binding.textRegisterHere.setOnClickListener(this)
    }
}