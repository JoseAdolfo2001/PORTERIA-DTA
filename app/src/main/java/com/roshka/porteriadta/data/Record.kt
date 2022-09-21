package com.roshka.porteriadta.data

import com.roshka.porteriadta.network.FirebaseRecordDocument

data class Record(
    var data: MutableMap<String, Any> = hashMapOf(
        FirebaseRecordDocument.CI_SOCIO to "",
        FirebaseRecordDocument.NOMBRE_SOCIO to "",
        FirebaseRecordDocument.APELLIDO_SOCIO to "",
        FirebaseRecordDocument.CI_PORTERO to "",
        FirebaseRecordDocument.CORREO_PORTERO to "",
        FirebaseRecordDocument.NOMBRE_PORTERO to "",
        FirebaseRecordDocument.APELLIDO_PORTERO to "",
        FirebaseRecordDocument.TYPE to "",
        FirebaseRecordDocument.FECHA to "",
        FirebaseRecordDocument.IS_EXIT to false,

    )
)
