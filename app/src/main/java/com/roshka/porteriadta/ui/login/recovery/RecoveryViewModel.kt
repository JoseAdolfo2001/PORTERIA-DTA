package com.roshka.porteriadta.ui.login.recovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.roshka.porteriadta.data.Response

class RecoveryViewModel : ViewModel() {
    private val _isSuccessfulRecovery = MutableLiveData<Response>()
    val isSuccessfulRecovery: LiveData<Response>
        get() = _isSuccessfulRecovery

    fun resetPassword(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val response = Response(
                    true,
                    "Correo electrónico enviado con éxito para restablecer su contraseña"
                )
                _isSuccessfulRecovery.value = response
            } else {
                val response = Response(false, task.exception!!.message.toString())
                _isSuccessfulRecovery.value = response
            }
        }
    }
}