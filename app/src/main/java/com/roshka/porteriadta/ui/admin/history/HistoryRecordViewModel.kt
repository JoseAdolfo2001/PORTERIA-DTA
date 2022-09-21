package com.roshka.porteriadta.ui.admin.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.roshka.porteriadta.data.Member
import com.roshka.porteriadta.data.Record
import com.roshka.porteriadta.network.FirebaseCollections

class HistoryRecordViewModel : ViewModel() {
    val fb = FirebaseFirestore.getInstance()
    private val _arrayRecords = MutableLiveData<ArrayList<Record>>()
    val arrayRecords: LiveData<ArrayList<Record>>
        get() = _arrayRecords

    fun getListRecord() {
        val aux: ArrayList<Record> = ArrayList()
        fb.collection(FirebaseCollections.RECORDS).get().addOnSuccessListener {
            for (document in it) {
                val data = document.data
                val record = Record(data)
                record.data = data
                aux.add(record)
                println(record.data)
            }
            _arrayRecords.value = aux
        }
    }
}