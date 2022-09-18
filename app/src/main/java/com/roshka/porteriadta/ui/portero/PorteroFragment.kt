package com.roshka.porteriadta.ui.portero

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roshka.porteriadta.databinding.FragmentLoginBinding
import com.roshka.porteriadta.databinding.FragmentPorteroBinding
import com.roshka.porteriadta.ui.portero.recyclerView.SociosListAdapter

class PorteroFragment : Fragment() {
    lateinit var adapter:SociosListAdapter
    private lateinit var binding: FragmentPorteroBinding
    private lateinit var viewModel: PorteroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPorteroBinding.inflate(inflater, container, false)
        return binding.root
            }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[PorteroViewModel::class.java]
        viewModel.getListMembers()
        viewModel.arrayMembers.observe(viewLifecycleOwner, Observer {
                println(it)
                adapter = SociosListAdapter(it,binding.rvMembers,binding.cardView,
                    binding.tvNombre,binding.tvApellido ,binding.tvCedula , binding.tvSociosNumeros
                    ,binding.ivFoto,binding.btnCamara,binding.btnEnviar)
                binding.rvMembers.layoutManager = LinearLayoutManager(activity)
                binding.rvMembers.adapter = adapter
                val decoration = DividerItemDecoration(activity, LinearLayoutManager(activity).orientation)
                binding.rvMembers.addItemDecoration(decoration)

            })
        }
    }
