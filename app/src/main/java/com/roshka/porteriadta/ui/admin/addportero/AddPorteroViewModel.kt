package com.roshka.porteriadta.ui.admin.addportero

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.roshka.porteriadta.data.Portero
import com.roshka.porteriadta.data.Response
import com.roshka.porteriadta.data.User
import com.roshka.porteriadta.network.FirebaseCollections

class AddPorteroViewModel : ViewModel() {
    val db = FirebaseFirestore.getInstance()
    val mAuth1 = FirebaseAuth.getInstance()
    val mAuth2 = FirebaseAuth.getInstance()

    private val _isSuccessful = MutableLiveData<Response>()
    val isSuccessful: LiveData<Response>
        get() = _isSuccessful

    fun addPortero(user: User, portero: Portero) {
        mAuth2.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    mAuth2.signOut()
                    db.collection(FirebaseCollections.USERS).document(user.email)
                        .set(portero.data)
                        .addOnSuccessListener {
                            _isSuccessful.value = Response(true, "Usuario agregado correctamente")
                            Log.d(TAG, "DocumentSnapshot successfully written! $it")
                        }
                        .addOnFailureListener {
                            _isSuccessful.value = Response(false, it.message.toString())
                        }
                } else {
                    _isSuccessful.value = Response(false, task.exception!!.message.toString())
                }
            }
    }
}