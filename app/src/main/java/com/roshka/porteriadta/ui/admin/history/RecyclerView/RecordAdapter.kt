package com.roshka.porteriadta.ui.admin.history.RecyclerView

import android.app.Activity
import android.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roshka.porteriadta.R
import com.roshka.porteriadta.data.Record
import com.roshka.porteriadta.ui.admin.history.HistoryDetails

class RecordAdapter(val listRecord:List<Record>, val fechaString:String):RecyclerView.Adapter<RecordViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RecordViewHolder(layoutInflater.inflate(R.layout.item_view_record,parent,false))
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val item = listRecord[position]
        holder.render(item,fechaString)
    }

    override fun getItemCount(): Int {
      return listRecord.size
    }
}