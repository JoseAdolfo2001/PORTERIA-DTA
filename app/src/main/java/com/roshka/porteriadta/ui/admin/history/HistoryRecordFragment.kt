package com.roshka.porteriadta.ui.admin.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.roshka.porteriadta.data.Member
import com.roshka.porteriadta.data.Record
import com.roshka.porteriadta.databinding.FragmentHistoryRecordBinding
import com.roshka.porteriadta.ui.admin.history.RecyclerView.RecordAdapter
import com.roshka.porteriadta.ui.portero.SwipeToDelete
import com.roshka.porteriadta.ui.portero.recyclerView.SociosListAdapter

class HistoryRecordFragment : Fragment() {
    private var _binding: FragmentHistoryRecordBinding? = null
    var array = listOf<Record>()
    lateinit var adapter: RecordAdapter


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = HistoryRecordFragment()
    }

    private lateinit var viewModel: HistoryRecordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryRecordBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[HistoryRecordViewModel::class.java]
        viewModel.getListRecord()
        viewModel.arrayRecords.observe(viewLifecycleOwner, Observer {
            println(it)
            array = it
            adapter = RecordAdapter(it)
            binding.rvRecord.layoutManager = LinearLayoutManager(activity)
            binding.rvRecord.adapter = adapter
            val decoration =
                DividerItemDecoration(activity, LinearLayoutManager(activity).orientation)
            binding.rvRecord.addItemDecoration(decoration)

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}