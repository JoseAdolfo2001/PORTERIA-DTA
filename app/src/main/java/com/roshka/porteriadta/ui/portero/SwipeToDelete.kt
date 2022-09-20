package com.roshka.porteriadta.ui.portero

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.roshka.porteriadta.ui.portero.recyclerView.SociosListAdapter

class SwipeToDelete(var adapter:SociosListAdapter):ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        var pos = viewHolder.adapterPosition
        adapter.deleteItem(pos)
    }
}