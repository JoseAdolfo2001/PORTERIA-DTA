package com.roshka.porteriadta.data

import com.roshka.porteriadta.network.FirebaseUsersDocument

data class Portero(
    val data: MutableMap<String, String> = hashMapOf(
        FirebaseUsersDocument.NAME to "",
        FirebaseUsersDocument.CI to "",
        FirebaseUsersDocument.ROL to "portero"
    )
)
