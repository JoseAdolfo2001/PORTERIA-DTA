package com.roshka.porteriadta.ui.admin.disabledportero

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roshka.porteriadta.data.User
import com.roshka.porteriadta.databinding.ItemDisablePorteroBinding
import com.roshka.porteriadta.network.FirebaseUsersDocument

class DisabledPorteroAdapter(val porteroList: List<User>) :
    RecyclerView.Adapter<DisabledPorteroAdapter.DisabledPorteroViewModel>() {

    inner class DisabledPorteroViewModel(private val itemUserBinding: ItemDisablePorteroBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bindItem(user: User) {
            itemUserBinding.tvName.text =
                "${user.data[FirebaseUsersDocument.NAME]} ${user.data[FirebaseUsersDocument.SURNAME]}"
            itemUserBinding.tvCi.text = "Nº Cédula: ${user.data[FirebaseUsersDocument.CI]}"
            itemUserBinding.tvEmail.text=user.email

            if(user.data[FirebaseUsersDocument.ACTIVE] == false) {
                itemUserBinding.tvRemove.visibility = View.GONE
                itemUserBinding.tvAdd.visibility = View.VISIBLE
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisabledPorteroViewModel {
        return DisabledPorteroViewModel(
            ItemDisablePorteroBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DisabledPorteroViewModel, position: Int) {
        val member = porteroList[position]
        holder.bindItem(member)
    }

    override fun getItemCount(): Int {
        return porteroList.size
    }

}

