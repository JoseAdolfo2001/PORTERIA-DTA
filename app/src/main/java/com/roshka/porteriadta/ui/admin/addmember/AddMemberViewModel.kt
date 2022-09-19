package com.roshka.porteriadta.ui.admin.addmember

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class AddMemberViewModel : ViewModel() {
    val db = FirebaseFirestore.getInstance()
   fun setMember(apellido : String , nombre : String , cedula : String,nsocios:String ,tipo : String){
       db.collection("List").document(cedula).set(hashMapOf("apellido" to apellido,
       "nombre" to nombre,"socios_numeros" to nsocios , "puede_entrar" to true , "tipo" to tipo))
   }
}



