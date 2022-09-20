package com.roshka.porteriadta.ui.portero.allMembers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.roshka.porteriadta.data.Member
import com.roshka.porteriadta.network.FirebaseCollections

class SearchMemberViewModel : ViewModel() {
    val db = FirebaseFirestore.getInstance()
    private val _arrayMembers = MutableLiveData<List<Member>>()
    val arrayMembers: LiveData<List<Member>>
        get() = _arrayMembers

    fun getListMembers() {
        val aux: MutableList<Member> = mutableListOf()
        db.collection(FirebaseCollections.MEMBERS).get()
            .addOnSuccessListener {
                for (document in it) {
                    val data = document.data
                    val member = Member(document.id)
                    member.data = data
                    aux.add(member)
                    println(member.data)
                }
                _arrayMembers.value = aux
            }
            .addOnFailureListener {

            }
    }
}