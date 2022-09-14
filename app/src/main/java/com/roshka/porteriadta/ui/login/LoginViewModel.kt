package com.roshka.porteriadta.ui.login

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception
import kotlin.coroutines.coroutineContext
import com.roshka.porteriadta.AdminActivity as AdminActivity1

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    val fb = FirebaseFirestore.getInstance()
    var tipoAcceso = ""
    val context = getApplication<Application>().applicationContext
    var et_email = "hola"
    var et_password = "hola"
    val flag = MutableLiveData<Boolean>()
    val code = MutableLiveData<Int>()

    fun loginUsers(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            code.value = 1
        } else {

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {

                    if (it.isSuccessful) {

                        fb.collection("Users").document(email).get()
                            .addOnSuccessListener { result ->
                                tipoAcceso = result.get("Nivel").toString()
                                if (tipoAcceso == "admin") {
                                    code.value = 2
                                    flag.value = true

                                } else {
                                    flag.value = false
                                    code.value = 3
                                }
                            }
                    } else {
                        code.value = 4
                    }
                }


        }

    }
}

