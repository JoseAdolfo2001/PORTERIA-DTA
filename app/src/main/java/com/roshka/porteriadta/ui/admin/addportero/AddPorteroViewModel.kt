package com.roshka.porteriadta.ui.admin.addportero

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.test.core.app.ApplicationProvider
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.roshka.porteriadta.data.Response
import com.roshka.porteriadta.data.User
import com.roshka.porteriadta.network.FirebaseCollections


class AddPorteroViewModel : ViewModel() {
    val db = FirebaseFirestore.getInstance()
    private var mAuth1: FirebaseAuth? = null
    private var mAuth2: FirebaseAuth? = null

    private val _isSuccessful = MutableLiveData<Response>()
    val isSuccessful: LiveData<Response>
        get() = _isSuccessful
    fun addPortero(user: User) {
        mAuth1 = FirebaseAuth.getInstance()
        val firebaseOptions = FirebaseOptions.Builder()
            .setDatabaseUrl("[https://console.firebase.google.com/project/porteria-dta-test/firestore/data/~2FUsers~2Fa@a.com]")
            .setApiKey("AIzaSyCG0KX3q9FVEiB985l4ujmy16djmvPhhUE")
            .setApplicationId("porteria-dta-test").build()
        try {
            val myApp = FirebaseApp.initializeApp(
                ApplicationProvider.getApplicationContext<Context>(),
                firebaseOptions,
                "AnyAppName"
            )
            mAuth2=FirebaseAuth.getInstance(myApp)
        } catch (e: IllegalStateException) {
            mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"))
        }


        mAuth2?.createUserWithEmailAndPassword(user.email, user.password)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    mAuth2?.signOut()
                    db.collection(FirebaseCollections.USERS).document(user.email)
                        .set(user.data)
                        .addOnSuccessListener {
                            _isSuccessful.value = Response(true, "Usuario agregado correctamente")
                            Log.d(TAG, "DocumentSnapshot successfully written!")
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