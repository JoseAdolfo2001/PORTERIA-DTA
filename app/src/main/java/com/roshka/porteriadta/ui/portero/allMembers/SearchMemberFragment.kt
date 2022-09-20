package com.roshka.porteriadta.ui.portero.allMembers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.roshka.porteriadta.databinding.FragmentSearchMemberBinding

class SearchMemberFragment : Fragment() {
    private lateinit var binding: FragmentSearchMemberBinding

    companion object {
        fun newInstance() = SearchMemberFragment()
    }

    private lateinit var viewModel: SearchMemberViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentSearchMemberBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchMemberViewModel::class.java)

        viewModel.getListMembers()

        viewModel.arrayMembers.observe(viewLifecycleOwner) {
            binding.rwMembers.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            val adapter = MembersAdapter(it)
            binding.rwMembers.adapter = adapter
        }
    }

}