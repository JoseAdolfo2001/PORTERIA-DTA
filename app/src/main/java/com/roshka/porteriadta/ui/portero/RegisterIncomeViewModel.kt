package com.roshka.porteriadta.ui.portero

import android.app.Activity
import android.app.ProgressDialog
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.roshka.porteriadta.data.Member
import com.roshka.porteriadta.network.FirebaseCollections
import com.roshka.porteriadta.network.FirebaseMemberDocument
import java.text.SimpleDateFormat
import java.util.*


class RegisterIncomeViewModel : ViewModel() {
    val REQUEST_CAMERA = 1000
    val currentUser = FirebaseAuth.getInstance().currentUser
    val storageReference = FirebaseStorage.getInstance()
    val fb = FirebaseFirestore.getInstance()
    private val _arrayMembers = MutableLiveData<List<Member>>()
    val arrayMembers: LiveData<List<Member>>
        get() = _arrayMembers

    fun getListMembers() {
        val aux: MutableList<Member> = mutableListOf()
        fb.collection(FirebaseCollections.MEMBERS).get().addOnSuccessListener {
            for (document in it) {
                val data = document.data
                val member = Member(document.id)
                member.data = data
                aux.add(member)
                println(member.data)
            }
            _arrayMembers.value = aux
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
        var referenceImage = storageReference.getReference("images/${fileName}")
        referenceImage.putFile(foto!!).addOnSuccessListener {
            val uriTask = it.storage.downloadUrl
            while (!uriTask.isSuccessful());
            if (uriTask.isSuccessful()) {
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
}

