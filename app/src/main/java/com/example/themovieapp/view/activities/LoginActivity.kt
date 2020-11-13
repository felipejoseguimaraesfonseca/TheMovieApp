package com.example.themovieapp.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.themovieapp.R
import com.example.themovieapp.data.constants.UserConstants
import com.example.themovieapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: UserViewModel
    private var mUserId: Int = 0
    private var mEmail: String = ""
    private var mPassword: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        setListeners()

        observe()
    }

    override fun onClick(view: View) {
        val id = view.id

        if (id == R.id.buttonLogIn) {
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            if (email == "" && password == "") {
                Toast.makeText(this, getString(R.string.put_datas), Toast.LENGTH_SHORT).show()
            } else if ( email == "" || password == "") {
                Toast.makeText(this, getString(R.string.put_datas), Toast.LENGTH_SHORT).show()
            } else {
                loadData()
            }

        } else if (id == R.id.textRegisterHere) {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    private fun loadData(){
        val bundle = intent.extras
        if (bundle != null) {
            mUserId = bundle.getInt(UserConstants.USERID)
            mEmail = bundle.getString(UserConstants.EMAIL).toString()
            mPassword = bundle.getString(UserConstants.PASSWORD).toString()

            val getId = mViewModel.getUser(mUserId)
            val getLogin = mViewModel.login(mEmail, mPassword)

            val getIdInt = getId.toString().toInt()

            while (getIdInt > 0) {

                val getLoginString = getLogin.toString()

                if (getLoginString != "") {
                    val confirmEmail = editEmail.text.toString().toBoolean()
                    val confirmPassword = editPassword.text.toString().toBoolean()

                    val getLoginBoolean = getLoginString.toBoolean()

                    while (confirmEmail && confirmPassword == getLoginBoolean) {
                        startActivity(Intent(this, NavigationActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }

    private fun observe() {
        mViewModel.user.observe(this, Observer{
            if (it.toString().toBoolean()) {
                Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "Falha", Toast.LENGTH_LONG).show()
            }
            finish()
        })
    }

    private fun setListeners() {
        buttonLogIn.setOnClickListener(this)
        textRegisterHere.setOnClickListener(this)
    }
}