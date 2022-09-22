package com.roshka.porteriadta.ui.admin.addmember

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.roshka.porteriadta.data.Member
import com.roshka.porteriadta.data.Response
import com.roshka.porteriadta.network.FirebaseCollections
import com.roshka.porteriadta.network.FirebaseMemberDocument

class AddMemberViewModel : ViewModel() {
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    private val _isSuccessful = MutableLiveData<Response>()
    val isSuccessful: LiveData<Response>
        get() = _isSuccessful

    fun setMember(member: Member) {
        member.data[FirebaseMemberDocument.CREATED_BY] = user?.email.toString()
        db.collection(FirebaseCollections.MEMBERS).document(member.ci)
            .set(member.data)
            .addOnSuccessListener {
                _isSuccessful.value = Response(true, "Se Agrego Correctamente")
            }
            .addOnFailureListener {
                _isSuccessful.value = Response(false, it.message.toString())
            }
    }
}



