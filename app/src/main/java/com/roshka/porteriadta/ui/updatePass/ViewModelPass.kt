package com.roshka.porteriadta.ui.updatePass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ViewModelPass:ViewModel() {
    // The current word
    private val _currentPassword = MutableLiveData<String>()
    val currentPassword: LiveData<String>
        get() = _currentPassword
    private val _newPassword = MutableLiveData<String>()
    val newPassword: LiveData<String>
        get() = _newPassword
    private val _newPasswordConfirm = MutableLiveData<String>()
    val newPasswordConfirm: LiveData<String>
        get() = _newPasswordConfirm

    fun currentUser() = FirebaseAuth.getInstance().currentUser


    }
