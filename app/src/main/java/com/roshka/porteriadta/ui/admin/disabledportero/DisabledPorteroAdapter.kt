package com.roshka.porteriadta.ui.admin.disabledportero

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.roshka.porteriadta.AdminActivity
import com.roshka.porteriadta.data.Response
import com.roshka.porteriadta.data.User
import com.roshka.porteriadta.databinding.ItemDisablePorteroBinding
import com.roshka.porteriadta.network.FirebaseCollections
import com.roshka.porteriadta.network.FirebaseUsersDocument

class DisabledPorteroAdapter(val porteroList: List<User>) :
    RecyclerView.Adapter<DisabledPorteroAdapter.DisabledPorteroViewModel>() {
    inner class DisabledPorteroViewModel(private val itemUserBinding: ItemDisablePorteroBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bindItem(user: User) {
            val db = FirebaseFirestore.getInstance()
            itemUserBinding.tvName.text =
                "${user.data[FirebaseUsersDocument.NAME]} ${user.data[FirebaseUsersDocument.SURNAME]}"
            itemUserBinding.tvCi.text = "Nº Cédula: ${user.data[FirebaseUsersDocument.CI]}"
            itemUserBinding.tvEmail.text = user.email

            if (user.data[FirebaseUsersDocument.ACTIVE] == "No") {
                itemUserBinding.tvRemove.visibility = View.GONE
                itemUserBinding.tvAdd.visibility = View.VISIBLE

            }

            itemUserBinding.tvRemove.setOnClickListener {

                        itemUserBinding.tvRemove.visibility = View.GONE
                        itemUserBinding.tvAdd.visibility = View.VISIBLE
                        user.data[FirebaseUsersDocument.ACTIVE] = "No"
                        db.collection(FirebaseCollections.USERS).document(user.email)
                        .set(user.data)
                    }

            itemUserBinding.tvAdd.setOnClickListener {
                        itemUserBinding.tvAdd.visibility = View.GONE
                        itemUserBinding.tvRemove.visibility = View.VISIBLE
                        user.data[FirebaseUsersDocument.ACTIVE] = "Si"
                        db.collection(FirebaseCollections.USERS).document(user.email)
                            .set(user.data)

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

