package com.roshka.porteriadta.ui.portero.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roshka.porteriadta.R
import com.roshka.porteriadta.data.SociosInfo

class SociosListAdapter(val sociosList:List<SociosInfo>):RecyclerView.Adapter<SociosListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SociosListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SociosListViewHolder(layoutInflater.inflate(R.layout.socios_list_view,parent,false))

    }

    override fun onBindViewHolder(holder: SociosListViewHolder, position: Int) {
        val item = sociosList[position]
        val newArray = sociosList[position]
        holder.bind(item,newArray)

    }

    override fun getItemCount(): Int {
        return sociosList.size
    }
}