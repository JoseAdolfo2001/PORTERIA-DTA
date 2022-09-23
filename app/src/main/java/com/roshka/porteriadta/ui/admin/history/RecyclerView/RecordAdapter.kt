package com.roshka.porteriadta.ui.admin.history.RecyclerView

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.roshka.porteriadta.AdminActivity
import com.roshka.porteriadta.PorteroActivity
import com.roshka.porteriadta.R
import com.roshka.porteriadta.data.Record
import com.roshka.porteriadta.network.FirebaseRecordDocument
import com.roshka.porteriadta.ui.admin.history.HistoryDetails
import com.roshka.porteriadta.ui.admin.history.HistoryRecordFragment
import com.roshka.porteriadta.ui.admin.history.HistoryRecordViewModel

class RecordAdapter(val listRecord:List<Record>,val btnFiltrar:Button,val btnInicial:Button,val btnFinal:Button,val activity: AdminActivity,
                    val et1:EditText,val et2:EditText,val rv:RecyclerView):RecyclerView.Adapter<RecordViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RecordViewHolder(layoutInflater.inflate(R.layout.item_view_record,parent,false))


    }


    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val item = listRecord[position]
        holder.render(item,btnFiltrar,btnInicial,btnFinal,activity,et1,et2,rv)


    }

    override fun getItemCount(): Int {
      return listRecord.size
    }


}