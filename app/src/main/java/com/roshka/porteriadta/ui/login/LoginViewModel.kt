package com.roshka.porteriadta.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    val fb = FirebaseFirestore.getInstance()
    var tipoAcceso = ""
    val auth = FirebaseAuth.getInstance()
    val dato = MutableLiveData<Int>()
    val flag = MutableLiveData<Boolean>()
    val code = MutableLiveData<Int>()
    val user = auth.currentUser

    fun loginUsers(email: String, password: String) {

        if (email.isEmpty() || password.isEmpty()) {
            code.value = 1
        } else {
            auth.signInWithEmailAndPassword(email, password)
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

