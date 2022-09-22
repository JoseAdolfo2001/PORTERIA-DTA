package com.roshka.porteriadta.ui.admin.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.roshka.porteriadta.data.FilterParameters
import com.roshka.porteriadta.data.Record
import com.roshka.porteriadta.network.FirebaseCollections
import com.roshka.porteriadta.network.FirebaseMemberDocument
import com.roshka.porteriadta.network.FirebaseRecordDocument
import java.util.*
import kotlin.collections.ArrayList

class HistoryRecordViewModel : ViewModel() {
    val fb = FirebaseFirestore.getInstance()
    private val _arrayRecords = MutableLiveData<ArrayList<Record>>()
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error
    private val listAllRecords = ArrayList<Record>()
    private val listAllRecordsFilter = ArrayList<Record>()
    val arrayRecords: LiveData<ArrayList<Record>>
        get() = _arrayRecords

    init {
        eventChangeListener()
    }

    private fun eventChangeListener() {
        fb.collection(FirebaseCollections.RECORDS)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        _error.value = error.message.toString()
                        return
                    } else {
                        for (dc: DocumentChange in value?.documentChanges!!) {
                            val data = dc.document.data
                            val record = Record(dc.document.data)
                            record.data = data
                            when (dc.type) {
                                DocumentChange.Type.ADDED -> {
                                    listAllRecords.add(dc.newIndex, record)
                                }
                                DocumentChange.Type.MODIFIED -> {
                                    listAllRecords[dc.oldIndex] = record
                                }
                                DocumentChange.Type.REMOVED -> {
                                    listAllRecords.removeAt(dc.oldIndex)
                                }
                            }
                        }
                        _arrayRecords.value = listAllRecords
                    }
                }
            })

    }

    fun onQueryTextChange(filterParameters: FilterParameters): Boolean {
        with(listAllRecordsFilter) { clear() }
        val dateStart = filterParameters.dateStart
        val dateEnd = filterParameters.dateEnd
        listAllRecords.forEach {
            val date = it.data[FirebaseRecordDocument.FECHA]
            val id_member = it.data[FirebaseMemberDocument.ID_MEMBER].toString()
                .lowercase(Locale.getDefault())
            val name =
                "${it.data[FirebaseMemberDocument.NAME]} ${it.data[FirebaseMemberDocument.SURNAME]}".lowercase(
                    Locale.getDefault())
            if (date in dateStart..dateEnd) {
                listAllRecordsFilter.add(it)
            }
        }
        _arrayRecords.value = listAllRecordsFilter
        return false
    }

}