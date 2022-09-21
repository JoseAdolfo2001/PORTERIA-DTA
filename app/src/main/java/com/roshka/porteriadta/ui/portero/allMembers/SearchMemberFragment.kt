package com.roshka.porteriadta.ui.portero.allMembers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
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
        binding.rwMembers.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[SearchMemberViewModel::class.java]

        viewModel.eventChangeListener()

        viewModel.arrayMembers.observe(viewLifecycleOwner) {
            val adapter = MembersAdapter(it)
            binding.rwMembers.adapter = adapter
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showAlert(it)
        }
    }

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
    }

}