package com.roshka.porteriadta.ui.login.recovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.roshka.porteriadta.data.RecoveryResponse

class RecoveryViewModel : ViewModel() {
    private val _isSuccessfulRecovery = MutableLiveData<RecoveryResponse>()
    val isSuccessfulRecovery: LiveData<RecoveryResponse>
        get() = _isSuccessfulRecovery

    fun resetPassword(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val response = RecoveryResponse(
                    true,
                    "Correo electrónico enviado con éxito para restablecer su contraseña"
                )
                _isSuccessfulRecovery.value = response
            } else {
                val response = RecoveryResponse(false, task.exception!!.message.toString())
                _isSuccessfulRecovery.value = response
            }
        }
    }
}