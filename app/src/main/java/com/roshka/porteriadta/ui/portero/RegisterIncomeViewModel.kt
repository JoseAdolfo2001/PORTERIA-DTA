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
import com.google.firebase.storage.StorageReference
import com.roshka.porteriadta.data.SociosInfo
import com.roshka.porteriadta.network.FirebaseCollections
import java.text.SimpleDateFormat
import java.util.*


class RegisterIncomeViewModel : ViewModel() {
    val REQUEST_CAMERA = 1000
    val currentUser = FirebaseAuth.getInstance().currentUser
    val storageReference = FirebaseStorage.getInstance()
    val fb = FirebaseFirestore.getInstance()
    private val _arrayMembers = MutableLiveData<List<SociosInfo>>()
    val arrayMembers: LiveData<List<SociosInfo>>
        get() = _arrayMembers

    fun getListMembers() {
        val aux: MutableList<SociosInfo> = mutableListOf()
        fb.collection(FirebaseCollections.LIST).get().addOnSuccessListener {
            for (document in it) {
                val data = document.data
                val example = SociosInfo(document.id,
                    data.get("nombre")!!,
                    data.get("apellido")!!,
                    data.get("socios_numeros")!!,
                    data.get("tipo")!!)
                aux.add(example)
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
//        progressDialog.setMessage("Actualizando foto")
//        progressDialog.show()
//        val rute_storage_photo: String =
//            storage_path.toString() + "" + photo + "" + mAuth.getUid() + "" + idd
//        val reference: StorageReference = storageReference.child(rute_storage_photo)
//        reference.putFile(image_url).addOnSuccessListener { taskSnapshot ->
//            val uriTask: Task<Uri> = taskSnapshot.storage.downloadUrl
//            while (!uriTask.isSuccessful());
//            if (uriTask.isSuccessful()) {
//                uriTask.addOnSuccessListener(OnSuccessListener<Uri> { uri ->
//                    val download_uri = uri.toString()
//                    val map: HashMap<String, Any> = HashMap()
//                    map["photo"] = download_uri
//                    mfirestore.collection("pet").document(idd).update(map)
//                    Toast.makeText(this@CreatePetActivity, "Foto actualizada", Toast.LENGTH_SHORT)
//                        .show()
//                    progressDialog.dismiss()
//                })
//            }
//        }.addOnFailureListener {
//            Toast.makeText(this@CreatePetActivity,
//                "Error al cargar foto",
//                Toast.LENGTH_SHORT).show()
//        }


    }

    fun registrer() {
        val formated = SimpleDateFormat("yyyy_MM_dd_HH-mm-ss", Locale.getDefault())
        val now = Date()
        val fileName = formated.format(now)
        val storageRef = storageReference.reference

        storageRef.child("images/${fileName}.jpeg").downloadUrl.addOnSuccessListener {
            println(it.toString() + "holaaaa")
        }.addOnFailureListener {
           println("falle kp")
        }

    }
}

