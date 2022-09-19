package com.roshka.porteriadta.ui.portero
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.roshka.porteriadta.data.SociosInfo
import com.roshka.porteriadta.network.FirebaseCollections

class RegisterIncomeViewModel : ViewModel() {
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
                println(data)
                val example = SociosInfo(document.id, data["nombre"]!!, data["apellido"]!!
                , data["socios_numeros"]!!, data["tipo"]!!)
                aux.add(example)
            }
            _arrayMembers.value = aux
        }
        }
    fun setPicture(){

    }
    }

