package com.example.themovieapp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.themovieapp.data.model.UserEntity
import com.example.themovieapp.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class UserViewModel(application: Application): AndroidViewModel(application) {
    
    private val mContext: Context = application.applicationContext
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
                mSaveUser.value = mUserRepository.save(user)
            } else {
                mSaveUser.value = mUserRepository.update(user)
            }
        }
    }

    fun getUser(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val getUserId = UserEntity().apply {
                this.id = id
            }
            mUser.value = mUserRepository.getUser(getUserId.toString().toInt())
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val getLogin = UserEntity().apply {
                this.email = email
                this.password = password
            }
            mUser.value = mUserRepository.login(getLogin.toString())
        }
    }
}