package com.roshka.porteriadta.ui.portero.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.roshka.porteriadta.R
import com.roshka.porteriadta.data.SociosInfo

class SociosListAdapter(val sociosList:List<SociosInfo>,
                        val rvMembers:RecyclerView,
                        val cardView:CardView,
                        val tv_nombre:TextView,
                        val tv_apellido:TextView,
                        val tv_cedula:TextView,
                        val tv_socios_numeros:TextView,
                        val iv_foto:ImageView,
                        val btn_camara:Button,
                        val btn_enviar:Button)
                            :RecyclerView.Adapter<SociosListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SociosListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SociosListViewHolder(layoutInflater.inflate(R.layout.socios_list_view,parent,false))
    }

    override fun onBindViewHolder(holder: SociosListViewHolder, position: Int) {
        val item = sociosList[position]
        holder.bind(item,rvMembers,cardView,tv_nombre,tv_apellido,
            tv_cedula,tv_socios_numeros,iv_foto,btn_camara,btn_enviar)

    }

    override fun getItemCount(): Int {
        return sociosList.size
    }
}