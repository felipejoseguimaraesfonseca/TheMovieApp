package com.example.themovieapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.themovieapp.data.model.UserEntity
import com.example.themovieapp.data.repository.UserRepository

class UserViewModel(application: Application): AndroidViewModel(application) {
    
    private val mContext = application.applicationContext
    private val mUserRepository: UserRepository = UserRepository(mContext)

    private val mSaveUser = MutableLiveData<Boolean>()
    val saveUser: LiveData<Boolean> = mSaveUser

    private val mUser = MutableLiveData<UserEntity>()
    val user: LiveData<UserEntity> = mUser

    fun save(id: Int, firstName: String, lastName: String, email: String, password: String) {
        val user = UserEntity().apply {
            this.id = id
            this.firstName
            this.lastName
            this.email
            this.password
        }

        if (id == 0) {
            mSaveUser.value = mUserRepository.save(user)
        } else {
            mSaveUser.value = mUserRepository.update(user)
        }
    }

    fun load(id: Int, email: String, password: String) {
        mUser.value = mUserRepository.get(id, email, password)
    }
}