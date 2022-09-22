package com.roshka.porteriadta.ui.portero.allMembers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.roshka.porteriadta.data.Member
import com.roshka.porteriadta.network.FirebaseCollections
import com.roshka.porteriadta.network.FirebaseMemberDocument
import java.util.*

class SearchMemberViewModel() : ViewModel() {
    val db = FirebaseFirestore.getInstance()
    val listAllMembers = ArrayList<Member>()
    private val listAllMembersFilter = ArrayList<Member>()

    private val _arrayMembers = MutableLiveData<ArrayList<Member>>()
    val arrayMembers: LiveData<ArrayList<Member>>
        get() = _arrayMembers

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        eventChangeListener()
    }

    private fun eventChangeListener() {
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
                val ci = it.ci.lowercase(Locale.getDefault())
                val id_member = it.data[FirebaseMemberDocument.ID_MEMBER].toString().lowercase(Locale.getDefault())
                val name = "${it.data[FirebaseMemberDocument.NAME]} ${it.data[FirebaseMemberDocument.SURNAME]}".lowercase(Locale.getDefault())
                if(ci.contains(searchText) || id_member.contains(searchText) || name.contains(searchText)) {
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

    fun getMember(position: Int) : Member {
        return _arrayMembers.value?.get(position) ?: Member("-1", mutableMapOf())
    }
}