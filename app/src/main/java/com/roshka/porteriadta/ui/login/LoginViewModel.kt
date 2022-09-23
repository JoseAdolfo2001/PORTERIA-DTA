package com.roshka.porteriadta.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.roshka.porteriadta.network.FirebaseCollections
import com.roshka.porteriadta.network.FirebaseUsersDocument

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val ADMIN = 1
    val PORTERO = 0

    val fb = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    private val _isSuccessfulLogin = MutableLiveData<Int>()
    val isSuccessfulLogin: LiveData<Int>
        get() = _isSuccessfulLogin

    private val _errorLogin = MutableLiveData<String>()
    val errorLogin: LiveData<String>
        get() = _errorLogin

    fun loginUsers(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fb.collection(FirebaseCollections.USERS).document(email).get()
                        .addOnSuccessListener { result ->
                            val type = result.get(FirebaseUsersDocument.ROL).toString()
                            val enabled = result.get(FirebaseUsersDocument.ACTIVE).toString()
                            if(enabled == "true") {
                                if (type == "admin") {
                                    _isSuccessfulLogin.value = ADMIN

                                } else {
                                    _isSuccessfulLogin.value = PORTERO
                                }
                            } else {
                                _errorLogin.value = "El usuario está deshabilitado"
                            }
                        }
                } else {
                    _errorLogin.value = task.exception!!.message.toString()
                }
            }

    }
}

