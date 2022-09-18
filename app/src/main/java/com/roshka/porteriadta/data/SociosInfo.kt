package com.roshka.porteriadta.data

import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


data class SociosInfo(
    val cedula: Any,
    val name: Any,
    val apellido: Any,
    val numero_socio: Any,
    var boolean:Boolean = false
)

data class ViewProvider(
    val rvMembers:RecyclerView,
    val cardView:CardView,
    val tv_nombre:TextView,
    val tv_apellido:TextView,
    val tv_cedula:TextView,
    val tv_socios_numeros:TextView
        )
