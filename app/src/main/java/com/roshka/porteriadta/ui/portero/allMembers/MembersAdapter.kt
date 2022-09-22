package com.roshka.porteriadta.ui.portero.allMembers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roshka.porteriadta.R
import com.roshka.porteriadta.data.Member
import com.roshka.porteriadta.data.Type
import com.roshka.porteriadta.databinding.ItemMemberBinding
import com.roshka.porteriadta.network.FirebaseMemberDocument

class MembersAdapter(
    private val memberList: List<Member>,
    private val onItemClicked: (position: Int) -> Unit
) :
    RecyclerView.Adapter<MembersAdapter.MembersViewHolder>() {

    inner class MembersViewHolder(
        private val itemMemberBinding: ItemMemberBinding,
        private val onItemClicked: (position: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(itemMemberBinding.root), View.OnClickListener {
        @SuppressLint("SetTextI18n")
        fun bindItem(member: Member) {
            itemMemberBinding.tvName.text =
                "${member.data[FirebaseMemberDocument.NAME]} ${member.data[FirebaseMemberDocument.SURNAME]}"
            itemMemberBinding.tvCi.text = "Nº Cédula: ${member.ci}"
            val idMember = member.data[FirebaseMemberDocument.ID_MEMBER]
            if (idMember == null) {
                itemMemberBinding.tvSocio.visibility = View.GONE
            } else {
                itemMemberBinding.tvSocio.text = "Nº Socio: $idMember"
            }

            when (member.data[FirebaseMemberDocument.TYPE].toString()) {
                Type.SOCIO -> itemMemberBinding.typeMember.setImageResource(R.drawable.socio)
                Type.FIESTA -> itemMemberBinding.typeMember.setImageResource(R.drawable.fiesta)
                Type.GIMNASIO -> itemMemberBinding.typeMember.setImageResource(R.drawable.gimnasio)
                Type.GUARDERIA -> itemMemberBinding.typeMember.setImageResource(R.drawable.guarderia)
                Type.STAFF -> itemMemberBinding.typeMember.setImageResource(R.drawable.staff)
                Type.INVITADO -> itemMemberBinding.typeMember.setImageResource(R.drawable.invitado)
                Type.RESTATURANTE -> itemMemberBinding.typeMember.setImageResource(R.drawable.restaurante)
                else -> itemMemberBinding.typeMember.setBackgroundResource(R.drawable.incognito)
            }

            if (member.data[FirebaseMemberDocument.IS_DEFAULTER] == true) {
                itemView.setBackgroundResource(R.color.disabled)
            }
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            onItemClicked(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersViewHolder {
        return MembersViewHolder(
            ItemMemberBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClicked
        )
    }

    override fun onBindViewHolder(holder: MembersViewHolder, position: Int) {
        val member = memberList[position]
        holder.bindItem(member)
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

}