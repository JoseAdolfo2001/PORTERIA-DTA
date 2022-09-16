package com.roshka.porteriadta.ui.portero

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.roshka.porteriadta.data.SociosInfo
import com.roshka.porteriadta.network.FirebaseCollections

class PorteroViewModel : ViewModel() {
    val fb = FirebaseFirestore.getInstance()
    private val _arrayMembers = MutableLiveData<List<SociosInfo>>()
    val arrayMembers:LiveData<List<SociosInfo>>
        get() = _arrayMembers


    fun getListMembers(){
        val aux:MutableList<SociosInfo> = mutableListOf()
        fb.collection(FirebaseCollections.LIST).get().addOnSuccessListener {
            for(document in it){
                val data = document.data
                val example = SociosInfo(document.id, data.get("nombre")!!,data.get("apellido")!!
                ,data.get("socios_numeros")!!)
                aux.add(example)
            }
            _arrayMembers.value = aux
            println(_arrayMembers.value)
            }

        }
    }

