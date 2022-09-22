package com.roshka.porteriadta.ui.portero

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.storage.FirebaseStorage
import com.roshka.porteriadta.data.Member
import com.roshka.porteriadta.data.Record
import com.roshka.porteriadta.data.Response
import com.roshka.porteriadta.network.FirebaseCollections
import com.roshka.porteriadta.network.FirebaseMemberDocument
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class PorteroActivityViewModel : ViewModel() {
    val REQUEST_CAMERA = 1000
    val currentUser = FirebaseAuth.getInstance().currentUser
    val storageReference = FirebaseStorage.getInstance()

    private val _addMembers = MutableLiveData<ArrayList<Member>>()
    val addMembers: LiveData<ArrayList<Member>>
        get() = _addMembers
    private val auxAddMembers = arrayListOf<Member>()

    private val db = FirebaseFirestore.getInstance()
    private val listAllMembers = ArrayList<Member>()
    private val listAllMembersFilter = ArrayList<Member>()

    private val _arrayMembers = MutableLiveData<ArrayList<Member>>()
    val arrayMembers: LiveData<ArrayList<Member>>
        get() = _arrayMembers

    private val _isSuccessful = MutableLiveData<Response>()
    val isSuccessful: LiveData<Response>
        get() = _isSuccessful

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

    fun updateAddMember(position: Int) {
        val member = _arrayMembers.value?.get(position)
        if (member != null) {
            auxAddMembers.add(member)
            _addMembers.value = auxAddMembers
            _arrayMembers.value = listAllMembers
        }
    }

    fun uploadImages(ivFoto: ImageView, activity: Activity, foto: Uri) {
        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Uploading file")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val formated = SimpleDateFormat("yyyy_MM_dd_HH-mm-ss", Locale.getDefault())
        val now = Date()
        val fileName = formated.format(now)
        var referenceImage = storageReference.getReference("images/${fileName}${currentUser!!.email}")
        referenceImage.putFile(foto!!).addOnSuccessListener {
            val uriTask = it.storage.downloadUrl
            while (!uriTask.isSuccessful);
            if (uriTask.isSuccessful) {
                uriTask.addOnSuccessListener(OnSuccessListener<Uri> { uri ->
                    val download_uri = uri.toString()
                    println(download_uri)
                })
                    .addOnFailureListener {
                    }
            }
            ivFoto.setImageURI(null)
            Toast.makeText(activity, "Se cargo correctamente", Toast.LENGTH_SHORT).show()
            if (progressDialog.isShowing) progressDialog.dismiss()
        }.addOnFailureListener {
            if (progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(activity, "No se cargo correctamente", Toast.LENGTH_SHORT).show()
        }
    }

    fun sendRecord () {
//        db.collection(FirebaseCollections.USERS).document(user.email)
//            .set(user.data)
//            .addOnSuccessListener {
//                _isSuccessful.value = Response(true, "Usuario agregado correctamente")
//                Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!")
//            }
//            .addOnFailureListener {
//                _isSuccessful.value = Response(false, it.message.toString())
//            }
    }
}

