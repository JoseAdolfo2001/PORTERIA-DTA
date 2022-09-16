package com.roshka.porteriadta.ui.portero.recyclerView

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.roshka.porteriadta.R
import com.roshka.porteriadta.data.SociosInfo
import com.roshka.porteriadta.ui.portero.PorteroFragment

class SociosListViewHolder(view: View):RecyclerView.ViewHolder(view) {

    var tv_name: TextView = view.findViewById(R.id.tv_cedula)
    var tv_apellido: TextView = view.findViewById(R.id.tv_nombre)
    var tv_cedula: TextView = view.findViewById(R.id.tv_cedula)
    var tv_socio_numero: TextView = view.findViewById(R.id.tv_socios_numeros)

    fun bind(item: SociosInfo,newArray:SociosInfo) {
        if(item.boolean == false) {itemView.setVisibility(View.VISIBLE)};
        else itemView.setVisibility(View.GONE)
        tv_name.setText("Nombre:${item.name}")
        tv_apellido.setText("Apellido:${item.apellido}")
        tv_cedula.setText("Cedula:${item.cedula}")
        tv_socio_numero.setText("Numero Socio:${item.numero_socio}")
        itemView.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val activity = v!!.context as AppCompatActivity
                val argumentNombre = Bundle()
                argumentNombre.putBoolean("flag",true)
                val fragment = PorteroFragment()
                fragment.arguments = argumentNombre
                println(argumentNombre)

                activity.supportFragmentManager.beginTransaction().replace(R.id.porteroFragment,PorteroFragment()).addToBackStack(null).commit()
            }
        })

    }
}
