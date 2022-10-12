package com.roshka.porteriadta.ui.admin.history.RecyclerView

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roshka.porteriadta.AdminActivity
import com.roshka.porteriadta.R
import com.roshka.porteriadta.data.Record
import com.roshka.porteriadta.data.Type
import com.roshka.porteriadta.network.FirebaseMemberDocument
import com.roshka.porteriadta.network.FirebaseRecordDocument
import com.roshka.porteriadta.ui.admin.history.HistoryDetails
import java.text.SimpleDateFormat
import java.util.*

class RecordViewHolder(view: View):RecyclerView.ViewHolder(view) {
   var tv_cedula_socio = view.findViewById<TextView>(R.id.tv_cedula_socio)
   var tv_nombre_socio = view.findViewById<TextView>(R.id.tv_nombre_socio)
   var tv_ci_portero = view.findViewById<TextView>(R.id.tv_ci_portero)
   var tv_fecha_hora = view.findViewById<TextView>(R.id.tv_fecha_hora)
    var iv_type = view.findViewById<ImageView>(R.id.iv_type)
    var iv_is_exit = view.findViewById<ImageView>(R.id.iv_isExit)
    fun render(item:Record,btnFiltrar:Button,btnFechaFinal:Button,btnFechaInicial: Button
               ,activity: AdminActivity,rv:RecyclerView,btnLimpiar:Button) {
        val formated = SimpleDateFormat("yyyy/MM/dd/HH-mm-ss", Locale.getDefault())
        var fecha = Date()
        fecha.time = item.data[FirebaseRecordDocument.DATE_TIME].toString().toLong()
        var dateEnd = formated.format(fecha.time)
        tv_cedula_socio.text = "N° de cédula socio : "+item.data[FirebaseRecordDocument.CI_MEMBER].toString()
        tv_nombre_socio.text = item.data[FirebaseRecordDocument.NAME_MEMBER].toString() +" "+ item.data[FirebaseRecordDocument.SURNAME_MEMBER].toString()
        tv_ci_portero.text = "N° de cédula portero: "+ item.data[FirebaseRecordDocument.CI_PORTERO].toString()
        tv_fecha_hora.text = "Fecha y hora:"+ dateEnd
        if(item.data[FirebaseRecordDocument.IS_EXIT] == true){
            iv_is_exit.setImageResource(R.drawable.salida)
        }else iv_is_exit.setImageResource(R.drawable.entrada)
        when (item.data[FirebaseMemberDocument.TYPE].toString()) {
            Type.SOCIO -> iv_type.setImageResource(R.drawable.socio)
            Type.FIESTA -> iv_type.setImageResource(R.drawable.fiesta)
            Type.GIMNASIO -> iv_type.setImageResource(R.drawable.gimnasio)
            Type.GUARDERIA -> iv_type.setImageResource(R.drawable.guarderia)
            Type.STAFF -> iv_type.setImageResource(R.drawable.staff)
            Type.INVITADO -> iv_type.setImageResource(R.drawable.invitado)
            Type.RESTATURANTE -> iv_type.setImageResource(R.drawable.restaurante)
        }


        itemView.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                btnFiltrar.visibility = View.GONE
                btnLimpiar.visibility = View.GONE
                btnFechaFinal.visibility = View.GONE
                btnFechaInicial.visibility =View.GONE
                rv.visibility =View.GONE
                val argumentNombre = Bundle()
                argumentNombre.putSerializable("objeto",item)
                val fragment = HistoryDetails()
                fragment.arguments = argumentNombre

                activity.supportFragmentManager.beginTransaction().replace(R.id.cl_history_record,fragment)

                    .commit()

            }
        })

            }

        }





