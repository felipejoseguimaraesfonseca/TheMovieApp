package com.example.themovieapp.view.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.themovieapp.R
import com.example.themovieapp.data.model.UserEntity
import com.example.themovieapp.databinding.FragmentMyAccountBinding
import com.example.themovieapp.view.activities.LoginActivity
import com.example.themovieapp.view.activities.UpdateActivity
import com.example.themovieapp.viewmodel.UserViewModel

class MyAccountFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentMyAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var mViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.textUserFirstName.text = UserEntity().firstName
        binding.textUserLastName.text = UserEntity().lastName
        binding.textUserEmail.text = UserEntity().email

        observe()

        setListeners()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_delete_account -> {
                val id = UserEntity().id
                val firstName = UserEntity().firstName.toString()
                val lastName = UserEntity().lastName.toString()
                val email = UserEntity().email.toString()
                val password = UserEntity().password.toString()

                val alertDialog = AlertDialog.Builder(context)
                    .setTitle(R.string.delete_title_message)
                    .setMessage(R.string.delete_account_message)
                    .setPositiveButton(R.string.yes) { _, _ ->
                        mViewModel.delete(id, firstName, lastName, email, password)
                        val intent = Intent(context, LoginActivity::class.java)
                        startActivity(intent)
                        onDestroy()
                    }
                    .setNegativeButton(R.string.no) { _, _ ->
                        Toast.makeText(
                            context,
                            R.string.delete_no_account_message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }.create()

                alertDialog.show()
            }

            R.id.button_update_account -> {
                val intent = Intent(context, UpdateActivity::class.java)
                startActivity(intent)
                onDestroy()
            }

            R.id.button_exit_account -> {

                val alertDialog = AlertDialog.Builder(context)
                    .setTitle(R.string.logout_tilte_message)
                    .setMessage(R.string.logout_account_message)
                    .setPositiveButton(R.string.yes) { _, _ ->
                        val intent = Intent(context, LoginActivity::class.java)
                        startActivity(intent)
                        onDestroy()
                    }.setNegativeButton(R.string.no) { _, _ ->
                        Toast.makeText(
                            context,
                            R.string.logout_no_account_message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }.create()

                alertDialog.show()
            }
        }
    }

    private fun observe() {
        mViewModel.messageEventData.observe(viewLifecycleOwner, { stringResId ->
            Toast.makeText(context, stringResId, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setListeners() {
        binding.buttonDeleteAccount.setOnClickListener(this)
        binding.buttonUpdateAccount.setOnClickListener(this)
        binding.buttonExitAccount.setOnClickListener(this)
    }

}