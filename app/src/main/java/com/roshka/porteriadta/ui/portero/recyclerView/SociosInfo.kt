package com.roshka.porteriadta.ui.portero.recyclerView

import com.google.firebase.firestore.FirebaseFirestore
import com.roshka.porteriadta.network.FirebaseCollections

class SociosInfo(val fb: FirebaseFirestore = FirebaseFirestore.getInstance()){
    fun cargarDatos(){
        fb.collection(FirebaseCollections.LIST).get().addOnSuccessListener {
            for(document in it){
                println("${document.id} => ${document.data}")
                var hola = document.id

                println(document.data)

            }
        }.addOnFailureListener {
            println("Error kp ")
        }

    }
}
