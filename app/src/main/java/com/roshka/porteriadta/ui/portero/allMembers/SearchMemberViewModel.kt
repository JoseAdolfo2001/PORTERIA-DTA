package com.roshka.porteriadta.ui.portero.allMembers

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.*
import com.roshka.porteriadta.data.Member
import com.roshka.porteriadta.network.FirebaseCollections

class SearchMemberViewModel : ViewModel() {
    val db = FirebaseFirestore.getInstance()
    val aux = ArrayList<Member>()

    private val _arrayMembers = MutableLiveData<ArrayList<Member>>()
    val arrayMembers: LiveData<ArrayList<Member>>
        get() = _arrayMembers

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun eventChangeListener() {
        db.collection(FirebaseCollections.MEMBERS)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        _error.value = error.message.toString()
                        return
                    } else {
                        for (dc: DocumentChange in value?.documentChanges!!) {
                            val data = dc.document.data
                            val member = Member(dc.document.id)
                            member.data = data
                            val index = aux.indexOf(member)
                            println(member.data)
                            Log.d(
                                TAG,
                                "MEMBER: \n$index | ${dc.oldIndex} | ${dc.newIndex}"
                            )
                            when (dc.type) {
                                DocumentChange.Type.ADDED -> {
                                    aux.add(dc.newIndex, member)
                                }
                                DocumentChange.Type.MODIFIED -> {
                                    aux[dc.oldIndex] = member
                                }
                                DocumentChange.Type.REMOVED -> {
                                    aux.removeAt(dc.oldIndex)
                                }
                            }
                        }
                        _arrayMembers.value = aux
                    }
                }
            })

    }
}