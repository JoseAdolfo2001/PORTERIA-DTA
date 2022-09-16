package com.roshka.porteriadta.ui.updatePass

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class UpdatePassViewModel : ViewModel() {
    val code = MutableLiveData<Int>()
    var auth = FirebaseAuth.getInstance()
    fun changePass(password: String, newPass: String, newPassC: String) {
        val user = auth.currentUser
        if (user != null && user.email != null) {
            val credential = EmailAuthProvider
                .getCredential(user.email!!, password)
            user?.reauthenticate((credential))
                ?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        user?.updatePassword(newPass)
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    code.value = 2
                                    auth.signOut()
                                }
                                }
                            }else {
                        code.value = 3

                    }
                }

        }
    }
    }



