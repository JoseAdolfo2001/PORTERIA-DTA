package com.roshka.porteriadta.ui.admin.history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roshka.porteriadta.R

class HistoryRecordFragment : Fragment() {

    companion object {
        fun newInstance() = HistoryRecordFragment()
    }

    private lateinit var viewModel: HistoryRecordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history_record, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryRecordViewModel::class.java)
        // TODO: Use the ViewModel
    }

}