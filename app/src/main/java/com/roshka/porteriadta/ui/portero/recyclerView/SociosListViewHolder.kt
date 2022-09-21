package com.roshka.porteriadta.ui.portero.recyclerView

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roshka.porteriadta.R
import com.roshka.porteriadta.data.Member
import com.roshka.porteriadta.data.Type
import com.roshka.porteriadta.network.FirebaseMemberDocument


class SociosListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var tv_name: TextView = view.findViewById(R.id.tv_nombre_socio)
    var tv_cedula: TextView = view.findViewById(R.id.tv_cedula_socio)
    var tv_socio_numero: TextView = view.findViewById(R.id.tv_ci_portero)
    var iv_Tipo: ImageView = view.findViewById(R.id.iv_type)

    fun bind(item: Member) {
        when (item.data[FirebaseMemberDocument.TYPE].toString()) {
            Type().SOCIO -> iv_Tipo.setImageResource(R.drawable.socio)
            Type().FIESTA -> iv_Tipo.setImageResource(R.drawable.fiesta)
            Type().GIMNASIO -> iv_Tipo.setImageResource(R.drawable.gimnasio)
            Type().GUARDERIA -> iv_Tipo.setImageResource(R.drawable.guarderia)
            Type().STAFF -> iv_Tipo.setImageResource(R.drawable.staff)
            Type().INVITADO -> iv_Tipo.setImageResource(R.drawable.invitado)
            Type().RESTATURANTE -> iv_Tipo.setImageResource(R.drawable.restaurante)
            else -> this.iv_Tipo.setBackgroundResource(R.drawable.incognito)
        }
        tv_name.setText("${item.data[FirebaseMemberDocument.NAME]} ${item.data[FirebaseMemberDocument.SURNAME]}")
        this.tv_cedula.setText("Cedula: ${item.ci}")
        tv_socio_numero.setText("Numero Socio: ${item.data[FirebaseMemberDocument.ID_MEMBER]}")
        itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

            }
        })
    }


}
