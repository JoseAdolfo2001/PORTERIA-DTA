package com.roshka.porteriadta.ui.portero.recyclerView

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.roshka.porteriadta.R
import com.roshka.porteriadta.data.Member
import com.roshka.porteriadta.data.Tipo
import com.roshka.porteriadta.network.FirebaseMemberDocument


class SociosListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var tv_name: TextView = view.findViewById(R.id.tv_name)
    var tv_cedula: TextView = view.findViewById(R.id.tv_ci)
    var tv_socio_numero: TextView = view.findViewById(R.id.tv_socio)
    var iv_Tipo: ImageView = view.findViewById(R.id.iv_type)

    fun bind(
        item: Member,
        rvMembers: RecyclerView,
        cardView: CardView,
        tv_nombre: TextView,
        tv_apellido: TextView,
        tv_cedula: TextView,
        tv_socios_numeros: TextView,
        iv_foto: ImageView,
        btn_camara: FloatingActionButton,
        btn_enviar: Button,
        searchView: androidx.appcompat.widget.SearchView,
    ) {
        if (item.data[FirebaseMemberDocument.IS_DEFAULTER] == true) {
            itemView.setBackgroundResource(R.color.red)
        } else {
            itemView.setBackgroundResource(R.color.green)
        }
        when (item.data[FirebaseMemberDocument.TYPE].toString()) {
            Tipo().SOCIO -> iv_Tipo.setImageResource(R.drawable.socio)
            Tipo().FIESTA -> iv_Tipo.setImageResource(R.drawable.fiesta)
            Tipo().GIMNASIO -> iv_Tipo.setImageResource(R.drawable.gimnasio)
            Tipo().GUARDERIA -> iv_Tipo.setImageResource(R.drawable.guarderia)
            Tipo().STAFF -> iv_Tipo.setImageResource(R.drawable.staff)
            Tipo().INVITADO -> iv_Tipo.setImageResource(R.drawable.invitado)
            Tipo().RESTATURANTE -> iv_Tipo.setImageResource(R.drawable.restaurante)
            else -> iv_foto.setBackgroundResource(R.drawable.incognito)
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
