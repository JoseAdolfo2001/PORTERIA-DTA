package com.roshka.porteriadta.data

import com.roshka.porteriadta.network.FirebaseUsersDocument

data class User(
    val email : String,
    val password : String,
    var data: MutableMap<String, Any> = hashMapOf(
        FirebaseUsersDocument.NAME to "",
        FirebaseUsersDocument.SURNAME to "",
        FirebaseUsersDocument.CI to "",
        FirebaseUsersDocument.ROL to "portero",
        FirebaseUsersDocument.ACTIVE to ""
    )
)
