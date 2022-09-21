package com.roshka.porteriadta.ui.portero.allMembers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.roshka.porteriadta.data.Member
import com.roshka.porteriadta.network.FirebaseCollections
import java.util.*
import kotlin.collections.ArrayList

class SearchMemberViewModel : ViewModel() {
    val db = FirebaseFirestore.getInstance()
    val listAllMembers = ArrayList<Member>()
    val listAllMembersFilter = ArrayList<Member>()

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
                            when (dc.type) {
                                DocumentChange.Type.ADDED -> {
                                    listAllMembers.add(dc.newIndex, member)
                                }
                                DocumentChange.Type.MODIFIED -> {
                                    listAllMembers[dc.oldIndex] = member
                                }
                                DocumentChange.Type.REMOVED -> {
                                    listAllMembers.removeAt(dc.oldIndex)
                                }
                            }
                        }
                        _arrayMembers.value = listAllMembers
                    }
                }
            })

    }

    fun onQueryTextChange(newText: String) : Boolean {
        with(listAllMembersFilter) { clear() }
        val searchText = newText.lowercase(Locale.getDefault())
        if (searchText.isNotEmpty()){
            listAllMembers.forEach {
                if(it.ci.lowercase(Locale.getDefault()).contains(searchText)) {
                    listAllMembersFilter.add(it)
                }
            }
        } else {
            listAllMembersFilter.clear()
            listAllMembersFilter.addAll(listAllMembers)
        }
        _arrayMembers.value = listAllMembersFilter
        return false
    }
}