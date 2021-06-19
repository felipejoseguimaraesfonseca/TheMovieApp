package com.example.themovieapp.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.themovieapp.R
import com.example.themovieapp.databinding.ActivityRegisterBinding
import com.example.themovieapp.viewmodel.UserViewModel

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: UserViewModel
    private var mUserId: Int = 0

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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
        val idClick = view.id

        val firstName = binding.editFirstName.text.toString()
        val lastName = binding.editLastName.text.toString()
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

        if (idClick == R.id.buttonCancel) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else if (idClick == R.id.buttonSignUp) {
            if (firstName == "" && lastName == "" && email == "" && password == "") {
                Toast.makeText(
                    this,
                    getString(R.string.put_datas),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (firstName == "" || lastName == "" || email == "" || password == "") {
                Toast.makeText(
                    this,
                    getString(R.string.put_datas),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                mViewModel.save(mUserId, firstName, lastName, email, password)
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun observe() {
        mViewModel.messageEventData.observe(this, { stringResId ->
            Toast.makeText(this, stringResId, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setListeners() {
        binding.buttonCancel.setOnClickListener(this)
        binding.buttonSignUp.setOnClickListener(this)
    }
}