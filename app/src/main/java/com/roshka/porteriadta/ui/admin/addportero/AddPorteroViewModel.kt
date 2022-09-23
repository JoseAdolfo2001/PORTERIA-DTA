package com.roshka.porteriadta.ui.admin.addportero

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.roshka.porteriadta.data.Response
import com.roshka.porteriadta.data.User
import com.roshka.porteriadta.network.FirebaseCollections

class AddPorteroViewModel(application: Application) : AndroidViewModel(application) {
    val db = FirebaseFirestore.getInstance()
    var mAuth1 = FirebaseAuth.getInstance()
    lateinit var mAuth2 : FirebaseAuth


    private val _isSuccessful = MutableLiveData<Response>()
    val isSuccessful: LiveData<Response>
        get() = _isSuccessful
    fun addPortero(user: User) {
        val firebaseOptions = FirebaseOptions.Builder()
            .setApiKey("AIzaSyCG0KX3q9FVEiB985l4ujmy16djmvPhhUE")
            .setApplicationId("1:759541191344:android:9bceceea6daee880446175")
            .setProjectId("porteria-dta-test").build()
        mAuth2 = try {
            val myApp =
                FirebaseApp.initializeApp(getApplication(), firebaseOptions, "AnyAppName")
            FirebaseAuth.getInstance(myApp)
        } catch (e: IllegalStateException) {
            FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"))
        }
        mAuth2.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    mAuth2.signOut()
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