package com.roshka.porteriadta.ui.admin.addmember

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.roshka.porteriadta.data.Member
import com.roshka.porteriadta.network.FirebaseCollections

class AddMemberViewModel : ViewModel() {
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    fun setMember(member: Member) {
        db.collection(FirebaseCollections.MEMBERS).document(member.ci)
            .set(member.data)
    }
}



