package com.roshka.porteriadta.ui.change.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.roshka.porteriadta.data.Response

class ChangePasswordViewModel : ViewModel() {
    private val _isSuccessfulChange = MutableLiveData<Response>()
    val isSuccessfulChange: LiveData<Response>
        get() = _isSuccessfulChange

    val code = MutableLiveData<Int>()
    var auth = FirebaseAuth.getInstance()
    fun changePass(password: String, newPass: String) {
        val user = auth.currentUser
        if (user != null && user.email != null) {
            val credential = EmailAuthProvider
                .getCredential(user.email!!, password)
            user.reauthenticate((credential))
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        user.updatePassword(newPass)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val response = Response(true, "Contraseña actualizada correctamente")
                                    _isSuccessfulChange.value = response
                                    auth.signOut()
                                } else {
                                    val response = Response(false, task.exception!!.message.toString())
                                    _isSuccessfulChange.value = response
                                }
                            }
                    }else {
                        val response = Response(false, it.exception!!.message.toString())
                        _isSuccessfulChange.value = response
                    }
                }

        }
    }
}

