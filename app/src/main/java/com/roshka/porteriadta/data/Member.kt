package com.roshka.porteriadta.data

import com.roshka.porteriadta.network.FirebaseMemberDocument


data class Member(
    val ci: String,
    var data: MutableMap<String, Any> = hashMapOf(
        FirebaseMemberDocument.ID_MEMBER to "",
        FirebaseMemberDocument.NAME to "",
        FirebaseMemberDocument.SURNAME to "",
        FirebaseMemberDocument.IS_DEFAULTER to false,
        FirebaseMemberDocument.TYPE to ""
    )
)

