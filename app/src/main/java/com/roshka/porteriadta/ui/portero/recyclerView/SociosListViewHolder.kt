package com.roshka.porteriadta.ui.portero.recyclerView

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.roshka.porteriadta.R
import com.roshka.porteriadta.data.Member
import com.roshka.porteriadta.network.FirebaseMemberDocument

class SociosListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var tv_name: TextView = view.findViewById(R.id.tv_name)
    var tv_cedula: TextView = view.findViewById(R.id.tv_ci)
    var tv_socio_numero: TextView = view.findViewById(R.id.tv_socio)

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
        searchView: androidx.appcompat.widget.SearchView
    ) {
        if (item.data[FirebaseMemberDocument.IS_DEFAULTER] == true) {
            itemView.setBackgroundResource(R.color.red)
        } else {
            itemView.setBackgroundResource(R.color.green)
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
