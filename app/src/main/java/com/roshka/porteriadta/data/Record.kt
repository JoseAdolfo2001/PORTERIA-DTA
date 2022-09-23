package com.roshka.porteriadta.data

import com.roshka.porteriadta.network.FirebaseRecordDocument
import java.io.Serializable

data class Record(
    var data: MutableMap<String, Any> = hashMapOf(
        FirebaseRecordDocument.ID_MEMBER to "",
        FirebaseRecordDocument.CI_MEMBER to "",
        FirebaseRecordDocument.NAME_MEMBER to "",
        FirebaseRecordDocument.SURNAME_MEMBER to "",
        FirebaseRecordDocument.IS_DEFAULTER to "",
        FirebaseRecordDocument.IS_EXIT to false,
        FirebaseRecordDocument.IS_WALK to false,
        FirebaseRecordDocument.PHOTO to "",
        FirebaseRecordDocument.TYPE to "",
        FirebaseRecordDocument.CI_PORTERO to "",
        FirebaseRecordDocument.NAME_PORTERO to "",
        FirebaseRecordDocument.SURNAME_PORTERO to "",
        FirebaseRecordDocument.EMAIL_PORTERO to "",
        FirebaseRecordDocument.DATE_TIME to ""
    )
):Serializable
