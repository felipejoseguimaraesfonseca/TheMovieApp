package com.example.themovieapp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.example.themovieapp.data.model.UserEntity
import com.example.themovieapp.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class UserViewModel constructor(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val mUserRepository: UserRepository = UserRepository(mContext)

    private val mSaveUser = MutableLiveData<Boolean>()
    val saveUser: LiveData<Boolean> = mSaveUser

    private val mUser = MutableLiveData<UserEntity>()
    val user: LiveData<UserEntity> = mUser

    fun save(id: Int, firstName: String, lastName: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = UserEntity().apply {
                this.id = id
                this.firstName = firstName
                this.lastName = lastName
                this.email = email
                this.password = password
            }

            if (id == 0) {
                mSaveUser.postValue(mUserRepository.save(user))
            } else {
                mSaveUser.postValue(mUserRepository.update(user))
            }
        }
    }

    fun getUser(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val getUserId = UserEntity().apply {
                this.id = id
            }

            val getUserIdInt = getUserId.toString().toInt()

            mUser.postValue(mUserRepository.getUser(getUserIdInt))
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val getEmail = UserEntity().apply {
                this.email = email
            }

            val getPassword = UserEntity().apply {
                this.password = password
            }

            val getEmailString = getEmail.toString()
            val getPasswordString = getPassword.toString()

            mUser.postValue(mUserRepository.login(getEmailString, getPasswordString))
        }
    }
}