package com.roshka.porteriadta.ui.admin.history.RecyclerView

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roshka.porteriadta.R
import com.roshka.porteriadta.data.Record
import com.roshka.porteriadta.network.FirebaseRecordDocument

class RecordViewHolder(view: View):RecyclerView.ViewHolder(view) {
   var tv_cedula_socio = view.findViewById<TextView>(R.id.tv_cedula_socio)
   var tv_nombre_socio = view.findViewById<TextView>(R.id.tv_nombre_socio)
   var tv_ci_portero = view.findViewById<TextView>(R.id.tv_ci_portero)
   var tv_fecha_hora = view.findViewById<TextView>(R.id.tv_fecha_hora)
    fun render(item:Record) {
        tv_cedula_socio.text = "N° de cédula socio : "+item.data[FirebaseRecordDocument.CI_SOCIO].toString()
        tv_nombre_socio.text = item.data[FirebaseRecordDocument.NOMBRE_SOCIO].toString() +" "+ item.data[FirebaseRecordDocument.APELLIDO_SOCIO].toString()
        tv_ci_portero.text = "N° de cédula portero: "+ item.data[FirebaseRecordDocument.CI_PORTERO].toString()
        tv_fecha_hora.text = "Fecha y hora:"+item.data[FirebaseRecordDocument.FECHA].toString()
    }

}
