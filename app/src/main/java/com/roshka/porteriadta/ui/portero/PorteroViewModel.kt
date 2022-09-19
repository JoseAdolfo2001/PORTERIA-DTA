package com.roshka.porteriadta.ui.portero
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.roshka.porteriadta.data.SociosInfo
import com.roshka.porteriadta.network.FirebaseCollections

class PorteroViewModel : ViewModel() {
    val REQUEST_CAMERA = 1000
    val fb = FirebaseFirestore.getInstance()
    private val _arrayMembers = MutableLiveData<List<SociosInfo>>()
    val arrayMembers:LiveData<List<SociosInfo>>
        get() = _arrayMembers


    fun getListMembers(){
        val aux:MutableList<SociosInfo> = mutableListOf()
        fb.collection(FirebaseCollections.LIST).get().addOnSuccessListener {
            for(document in it){
                val data = document.data
                val example = SociosInfo(data.get("cedula")!!, data.get("nombre")!!,data.get("apellido")!!
                ,data.get("socios_numeros")!!)
                aux.add(example)
            }
            _arrayMembers.value = aux
        }

        }
    }

