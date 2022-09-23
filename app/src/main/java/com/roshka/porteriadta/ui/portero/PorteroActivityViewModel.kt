package com.roshka.porteriadta.ui.portero

import android.app.Activity
import android.app.ProgressDialog
import android.net.Uri
import android.text.format.Time
import android.widget.ImageView
import android.widget.Toast
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
import com.roshka.porteriadta.data.User
import com.roshka.porteriadta.network.FirebaseCollections
import com.roshka.porteriadta.network.FirebaseMemberDocument
import com.roshka.porteriadta.network.FirebaseRecordDocument
import com.roshka.porteriadta.network.FirebaseUsersDocument
import java.text.SimpleDateFormat
import java.util.*


class PorteroActivityViewModel : ViewModel() {
    val REQUEST_CAMERA = 1000
    private val auth = FirebaseAuth.getInstance().currentUser
    private val fb = FirebaseFirestore.getInstance()
    private lateinit var user: User
    var download_uri = ""

    private var is_exit = false
    private var is_walk = false

    private val storageReference = FirebaseStorage.getInstance()

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
        fb.collection(FirebaseCollections.USERS).document(auth?.email.toString()).get()
            .addOnSuccessListener {
                user = User(auth?.email.toString(), "", mutableMapOf())
                user.data[FirebaseUsersDocument.NAME] =
                    it.get(FirebaseUsersDocument.NAME).toString()
                user.data[FirebaseUsersDocument.SURNAME] =
                    it.get(FirebaseUsersDocument.SURNAME).toString()
                user.data[FirebaseUsersDocument.CI] = it.get(FirebaseUsersDocument.CI).toString()
                user.data[FirebaseUsersDocument.ROL] = it.get(FirebaseUsersDocument.ROL).toString()
                user.data[FirebaseUsersDocument.ACTIVE] =
                    it.get(FirebaseUsersDocument.ACTIVE).toString()
            }
            .addOnFailureListener {

            }
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

    fun onQueryTextChange(newText: String): Boolean {
        with(listAllMembersFilter) { clear() }
        val searchText = newText.lowercase(Locale.getDefault())
        if (searchText.isNotEmpty()) {
            listAllMembers.forEach {
                val ci = it.ci.lowercase(Locale.getDefault())
                val id_member = it.data[FirebaseMemberDocument.ID_MEMBER].toString()
                    .lowercase(Locale.getDefault())
                val name =
                    "${it.data[FirebaseMemberDocument.NAME]} ${it.data[FirebaseMemberDocument.SURNAME]}".lowercase(
                        Locale.getDefault()
                    )
                if (ci.contains(searchText) || id_member.contains(searchText) || name.contains(
                        searchText
                    )
                ) {
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
            if (searchMember(member)) {
                auxAddMembers.add(member)
                _addMembers.value = auxAddMembers
            }
            _arrayMembers.value = listAllMembers
        }
    }

    private fun searchMember(member: Member): Boolean {
        auxAddMembers.forEach {
            if (member.ci == it.ci) {
                return false
            }
        }
        return true
    }

    fun uploadImages(ivFoto: ImageView, activity: Activity, foto: Uri?) : Boolean {
        var flag : Boolean
        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Subiendo imagen")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val formated = SimpleDateFormat("yyyy_MM_dd_HH-mm-ss", Locale.getDefault())
        val now = Date()
        val fileName = formated.format(now)
        var referenceImage =
            storageReference.getReference("images/${fileName}${user.email}")
        if (foto != null) {
            referenceImage.putFile(foto).addOnSuccessListener {
                val uriTask = it.storage.downloadUrl
                while (!uriTask.isSuccessful);
                if (uriTask.isSuccessful) {
                    uriTask
                        .addOnSuccessListener { uri ->
                            download_uri = uri.toString()
                            println(download_uri)
                        }
                        .addOnFailureListener {
                        }
                }
                ivFoto.setImageURI(null)
                Toast.makeText(activity, "Se cargo correctamente", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
                flag = sendRecord()
            }.addOnFailureListener {
                if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(activity, "No se cargo correctamente", Toast.LENGTH_SHORT).show()
            }
        } else {
            progressDialog.dismiss()
            return sendRecord()
        }
        return false
    }

    fun sendRecord() : Boolean {
        val size = _addMembers.value?.size
        if (size == null || size == 0) {
            _isSuccessful.value = Response(false, "No se encontró ningún registro")
        } else {
            val collectionMember = db.collection(FirebaseCollections.RECORDS)
            var flag = false
            var message = ""
            _addMembers.value?.forEach {
                val record = Record()
                record.data[FirebaseRecordDocument.CI_MEMBER] = it.ci
                record.data[FirebaseRecordDocument.ID_MEMBER] =
                    it.data[FirebaseMemberDocument.ID_MEMBER].toString()
                record.data[FirebaseRecordDocument.NAME_MEMBER] =
                    it.data[FirebaseMemberDocument.NAME].toString()
                record.data[FirebaseRecordDocument.SURNAME_MEMBER] =
                    it.data[FirebaseMemberDocument.SURNAME].toString()
                record.data[FirebaseRecordDocument.IS_DEFAULTER] =
                    it.data[FirebaseMemberDocument.IS_DEFAULTER].toString()
                record.data[FirebaseRecordDocument.IS_EXIT] = this.is_exit
                record.data[FirebaseRecordDocument.IS_WALK] = this.is_walk
                record.data[FirebaseRecordDocument.TYPE] =
                    it.data[FirebaseMemberDocument.TYPE].toString()
                record.data[FirebaseRecordDocument.CI_PORTERO] =
                    user.data[FirebaseUsersDocument.CI].toString()
                record.data[FirebaseRecordDocument.NAME_PORTERO] =
                    user.data[FirebaseUsersDocument.NAME].toString()
                record.data[FirebaseRecordDocument.SURNAME_PORTERO] =
                    user.data[FirebaseUsersDocument.SURNAME].toString()
                record.data[FirebaseRecordDocument.EMAIL_PORTERO] = user.email
                record.data[FirebaseRecordDocument.PHOTO] = download_uri
                println(download_uri)
                val time = Time()
                time.setToNow()
                record.data[FirebaseRecordDocument.DATE_TIME] = time.toMillis(true).toString()

                collectionMember.add(record.data)
                    .addOnSuccessListener {
                        flag = true
                        println("FLAG: $flag")
                    }
                    .addOnFailureListener { it1 ->
                        flag = false
                        message = it1.message.toString()
                        _isSuccessful.value = Response(false, message)
                        return@addOnFailureListener
                    }
            }
            auxAddMembers.clear()
            _addMembers.value = auxAddMembers
            return true
        }
        return false
    }

    fun setIsWalk(is_walk: Boolean) {
        this.is_walk = is_walk
    }

    fun setIsExit(is_exit: Boolean) {
        this.is_exit = is_exit
    }

}

