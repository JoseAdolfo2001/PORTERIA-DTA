package com.roshka.porteriadta.ui.portero

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roshka.porteriadta.R
import com.roshka.porteriadta.databinding.FragmentLoginBinding

class PorteroFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    companion object {
        fun newInstance() = PorteroFragment()
    }

    private lateinit var viewModel: PorteroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[PorteroViewModel::class.java]
        // TODO: Use the ViewModel
    }

}