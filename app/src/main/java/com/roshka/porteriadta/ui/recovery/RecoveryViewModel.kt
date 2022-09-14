package com.roshka.porteriadta.ui.recovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
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
                val response = RecoveryResponse(true, task.exception!!.message.toString())
                _isSuccessfulRecovery.value = response
            }
        }
    }
}