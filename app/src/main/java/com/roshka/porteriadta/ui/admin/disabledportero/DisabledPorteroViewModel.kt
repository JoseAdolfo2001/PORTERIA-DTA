package com.roshka.porteriadta.ui.admin.disabledportero

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.roshka.porteriadta.data.User
import com.roshka.porteriadta.network.FirebaseCollections
import com.roshka.porteriadta.network.FirebaseUsersDocument
import java.util.*
import kotlin.collections.ArrayList

class DisabledPorteroViewModel : ViewModel() {
    val db = FirebaseFirestore.getInstance()
    val listAllUsers = ArrayList<User>()
    val listAllUsersFilter = ArrayList<User>()

    private val _arrayUsers = MutableLiveData<ArrayList<User>>()
    val arrayUsers: LiveData<ArrayList<User>>
        get() = _arrayUsers

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error
    private val _disable = MutableLiveData<User>()
    val disable: LiveData<User>
        get() = disable

    init {
        eventChangeListener()
    }

    private fun eventChangeListener() {
        db.collection(FirebaseCollections.USERS)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        _error.value = error.message.toString()
                        return
                    } else {
                        for (dc: DocumentChange in value?.documentChanges!!) {
                            val data = dc.document.data
                            val member = User(dc.document.id, "")
                            member.data = data
                            if(member.data[FirebaseUsersDocument.ROL].toString()  != "admin") {
                                when (dc.type) {
                                    DocumentChange.Type.ADDED -> {
                                        listAllUsers.add(dc.newIndex, member)
                                    }
                                    DocumentChange.Type.MODIFIED -> {
                                        listAllUsers[dc.oldIndex] = member
                                    }
                                    DocumentChange.Type.REMOVED -> {
                                        listAllUsers.removeAt(dc.oldIndex)
                                    }
                                }
                            }
                        }
                        _arrayUsers.value = listAllUsers
                    }
                }
            })

    }

    fun onQueryTextChange(newText: String): Boolean {
        with(listAllUsersFilter) { clear() }
        val searchText = newText.lowercase(Locale.getDefault())
        if (searchText.isNotEmpty()) {
            listAllUsers.forEach {
                val email = it.email.lowercase(Locale.getDefault())
                val name =
                    "${it.data[FirebaseUsersDocument.NAME]} ${it.data[FirebaseUsersDocument.SURNAME]}".lowercase(
                        Locale.getDefault()
                    )
                val ci = it.data[FirebaseUsersDocument.CI].toString().lowercase()
                if (email.contains(searchText) || name.contains(searchText) || ci.contains(
                        searchText
                    )
                ) {
                    listAllUsersFilter.add(it)
                }
            }


        } else {
            listAllUsersFilter.clear()
            listAllUsersFilter.addAll(listAllUsers)
        }
        _arrayUsers.value = listAllUsersFilter
        return false
    }

}


