package com.example.themovieapp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.*
import com.example.themovieapp.R
import com.example.themovieapp.data.model.UserEntity
import com.example.themovieapp.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel constructor(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val mContext = application.applicationContext
    private val mUserRepository: UserRepository = UserRepository(mContext)

    private val mSaveUser = MutableLiveData<Boolean>()
    private val mUpdateUser = MutableLiveData<Boolean>()
    private val mDeleteUser = MutableLiveData<Boolean>()

    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int>
        get() = _messageEventData

    fun save(id: Int, firstName: String, lastName: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = UserEntity().apply {
                this.id = id
                this.firstName = firstName
                this.lastName = lastName
                this.email = email
                this.password = password
            }

            try {
                if (id == 0) {
                    mSaveUser.postValue(mUserRepository.save(user))
                    _messageEventData.postValue(R.string.account_saved_successfully)
                } else {
                    mSaveUser.postValue(mUserRepository.update(user))
                    _messageEventData.postValue(R.string.account_updated_successfully)
                }
            } catch (exception: Exception) {
                _messageEventData.postValue(R.string.account_save_error)
                Log.e(TAG, exception.toString())
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val getUser = mUserRepository.login(email, password)

                if (getUser.email == email && getUser.password == password) {
                    mUserRepository.login(email, password)
                    _messageEventData.postValue(R.string.account_logged_successfully)
                }
            } catch (exception: Exception) {
                _messageEventData.postValue(R.string.account_login_error)
                Log.e(TAG, exception.toString())
            }
        }
    }

    fun updateUser(
        id: Int,
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val upadteUser = UserEntity().apply {
                this.id = id
                this.firstName = firstName
                this.lastName = lastName
                this.email = email
                this.password = password
            }

            try {
                if (id > 0) {
                    mUpdateUser.postValue(mUserRepository.update(upadteUser))
                    _messageEventData.postValue(R.string.account_updated_successfully)
                }
            } catch (exception: Exception) {
                _messageEventData.postValue(R.string.account_update_error)
                Log.e(TAG, exception.toString())
            }
        }
    }

    fun delete(id: Int, firstName: String, lastName: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val deleteUser = UserEntity().apply {
                this.id = id
                this.firstName = firstName
                this.lastName = lastName
                this.email = email
                this.password = password
            }

            try {
                if (id > 0) {
                    mDeleteUser.postValue(mUserRepository.delete(deleteUser))
                    _messageEventData.postValue(R.string.account_deleted_successfully)
                }
            } catch (exception: Exception) {
                _messageEventData.postValue(R.string.account_delete_error)
            }
        }
    }

    companion object {
        private val TAG = UserViewModel::class.java.simpleName
    }
}