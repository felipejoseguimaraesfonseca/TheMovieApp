package com.example.themovieapp.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.themovieapp.R
import com.example.themovieapp.databinding.ActivityUpdateBinding
import com.example.themovieapp.viewmodel.UserViewModel

class UpdateActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: UserViewModel
    private var mUserId: Int = 0

    private lateinit var binding: ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
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
        val firstName = binding.editFirstName.text.toString()
        val lastName = binding.editLastName.text.toString()
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

        when (view.id) {
            R.id.buttonCancel -> {
                val intent = Intent(this, NavigationActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.buttonUpdate -> {

                val alertDialog = AlertDialog.Builder(this)
                    .setTitle(R.string.update_title_message)
                    .setMessage(R.string.update_account_message)
                    .setPositiveButton(R.string.yes) { _, _ ->
                        mViewModel.updateUser(mUserId, firstName, lastName, email, password)
                        val intent = Intent(this, NavigationActivity::class.java)
                        startActivity(intent)
                        finish()
                    }.setNegativeButton(R.string.no) { _, _ ->
                        Toast.makeText(
                            this,
                            R.string.update_no_account_message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }.create()

                alertDialog.show()
            }
        }
    }

    private fun observe() {
        mViewModel.messageEventData.observe(this, { stringResId ->
            Toast.makeText(this, stringResId, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setListeners() {
        binding.buttonUpdate.setOnClickListener(this)
        binding.buttonCancel.setOnClickListener(this)
    }

}