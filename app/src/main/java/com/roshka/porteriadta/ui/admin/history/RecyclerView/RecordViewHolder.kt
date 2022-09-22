package com.roshka.porteriadta.ui.admin.history.RecyclerView



import android.view.View
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.roshka.porteriadta.R
import com.roshka.porteriadta.data.Record
import com.roshka.porteriadta.network.FirebaseRecordDocument
import com.roshka.porteriadta.ui.admin.history.HistoryRecordFragment
import java.text.SimpleDateFormat
import java.util.*

class RecordViewHolder(view: View):RecyclerView.ViewHolder(view) {
   var tv_cedula_socio = view.findViewById<TextView>(R.id.tv_cedula_socio)
   var tv_nombre_socio = view.findViewById<TextView>(R.id.tv_nombre_socio)
   var tv_ci_portero = view.findViewById<TextView>(R.id.tv_ci_portero)
   var tv_fecha_hora = view.findViewById<TextView>(R.id.tv_fecha_hora)
    fun render(item:Record,fechaString:String) {
        val formated = SimpleDateFormat("yyyy_MM_dd_HH-mm-ss", Locale.getDefault())
        var fecha = Date()
        fecha.time = item.data[FirebaseRecordDocument.FECHA].toString().toLong()
        var dateEnd = formated.format(fecha.time)
        tv_cedula_socio.text = "N° de cédula socio : "+item.data[FirebaseRecordDocument.CI_SOCIO].toString()
        tv_nombre_socio.text = item.data[FirebaseRecordDocument.NOMBRE_SOCIO].toString() +" "+ item.data[FirebaseRecordDocument.APELLIDO_SOCIO].toString()
        tv_ci_portero.text = "N° de cédula portero: "+ item.data[FirebaseRecordDocument.CI_PORTERO].toString()
        tv_fecha_hora.text = "Fecha y hora:"+ dateEnd
        if(item.data[FirebaseRecordDocument.FECHA].toString() == fechaString){
            itemView.visibility = View.INVISIBLE
        }
        itemView.setOnClickListener {
            findNavController(HistoryRecordFragment()).navigate(R.id.action_nav_disabled_portero_to_historyDetails)
        }

    }

}
