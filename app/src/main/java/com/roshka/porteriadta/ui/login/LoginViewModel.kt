package com.roshka.porteriadta.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.roshka.porteriadta.data.User
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

    fun loginUsers(user: User) {
        fb.collection(FirebaseCollections.USERS).document(user.email).get()
            .addOnSuccessListener { result ->
                user.data[FirebaseUsersDocument.ACTIVE] =
                    result.get(FirebaseUsersDocument.ACTIVE).toString()
                user.data[FirebaseUsersDocument.ROL] =
                    result.get(FirebaseUsersDocument.ROL).toString()
                if (user.data[FirebaseUsersDocument.ACTIVE] == FirebaseUsersDocument.ENABLED) {
                    auth.signInWithEmailAndPassword(user.email, user.password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                if (user.data[FirebaseUsersDocument.ROL] == "admin") {
                                    _isSuccessfulLogin.value = ADMIN

                                } else {
                                    _isSuccessfulLogin.value = PORTERO
                                }
                            } else {
                                _errorLogin.value = task.exception!!.message.toString()
                            }
                        }
                } else {
                    _errorLogin.value = "El usuario está deshabilitado"
                }
            }
            .addOnFailureListener {
                _errorLogin.value = it.message.toString()
            }
    }
}

