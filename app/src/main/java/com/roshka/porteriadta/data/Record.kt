package com.roshka.porteriadta.data

import com.roshka.porteriadta.network.FirebaseRecordDocument

data class Record(
    val data: MutableMap<String, Any> = hashMapOf(
        FirebaseRecordDocument.ID_MEMBER to "",
        FirebaseRecordDocument.ID_USER_PORTERO to "",
        FirebaseRecordDocument.DATE_TIME to "",
        FirebaseRecordDocument.IS_EXIT to false,
        FirebaseRecordDocument.PHOTO to ""
    )
)
