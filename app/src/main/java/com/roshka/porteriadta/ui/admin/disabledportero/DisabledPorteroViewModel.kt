package com.roshka.porteriadta.ui.admin.disabledportero

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.roshka.porteriadta.data.Member
import com.roshka.porteriadta.data.Response
import com.roshka.porteriadta.data.User
import com.roshka.porteriadta.network.FirebaseCollections

class DisabledPorteroViewModel : ViewModel() {
    // TODO: Implement the ViewMode
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    private val _isSuccessful = MutableLiveData<Response>()
    val isSuccessful: LiveData<Response>
        get() = _isSuccessful
    private val _arrayMembers = MutableLiveData<ArrayList<User>>()
    val arrayMembers: LiveData<ArrayList<User>>
        get() = _arrayMembers

    fun getListMembers() {
        val aux :ArrayList<User> =  ArrayList()
        db.collection(FirebaseCollections.USERS).get()
            .addOnSuccessListener {
                for (document in it) {
                    val data = document.data
                    val member = User(document.id, "")
                    member.data = data
                    aux.add(member)
                    println(member.data)
                }
                _arrayMembers.value = aux
            }
    }
        fun changeState(user : User){
            val docRef = db.collection(FirebaseCollections.USERS).document(user.email)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        db.collection(FirebaseCollections.USERS).document(user.email)
                            .set(user.data)
                            .addOnSuccessListener {
                                _isSuccessful.value = Response(true, "Se Suspendio Correctamente")
                            }
                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
        }

}


