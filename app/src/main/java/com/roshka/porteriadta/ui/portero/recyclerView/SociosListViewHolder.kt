package com.roshka.porteriadta.ui.portero.recyclerView

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.roshka.porteriadta.R
import com.roshka.porteriadta.data.SociosInfo

class SociosListViewHolder(view: View):RecyclerView.ViewHolder(view) {

    var tv_name: TextView = view.findViewById(R.id.tv_nombre)
    var tv_apellido: TextView = view.findViewById(R.id.tv_apellido)
    var tv_cedula: TextView = view.findViewById(R.id.tv_cedula)
    var tv_socio_numero: TextView = view.findViewById(R.id.tv_socios_numeros)

    fun bind(
        item: SociosInfo,
        rvMembers:RecyclerView,
        cardView:CardView,
        tv_nombre:TextView,
        tv_apellido:TextView,
        tv_cedula:TextView,
        tv_socios_numeros:TextView,
        iv_foto:ImageView,
        btn_camara: FloatingActionButton,
        btn_enviar:Button,
        tv_datos:TextView,
        searchView: androidx.appcompat.widget.SearchView
    ) {
        if(item.boolean == false) {itemView.setVisibility(View.VISIBLE)};
        else itemView.setVisibility(View.GONE)
        tv_name.setText("${item.name}")
        this.tv_apellido.setText("${item.apellido}")
        this.tv_cedula.setText("Cedula: ${item.cedula}")
        tv_socio_numero.setText("Numero Socio: ${item.numero_socio}")
        itemView.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
               rvMembers.visibility = View.GONE
                cardView.visibility = View.VISIBLE
                iv_foto.visibility = View.VISIBLE
                tv_datos.setText("Datos del socio")
                searchView.visibility = View.GONE
                btn_enviar.visibility = View.VISIBLE
                tv_nombre.text = item.name.toString()
                tv_apellido.text = item.apellido.toString()
                tv_cedula.text = "Cedula: " + item.cedula.toString()
                tv_socios_numeros.text = "Numero de Socio: " + item.numero_socio.toString()
            }
        })

    }
}
