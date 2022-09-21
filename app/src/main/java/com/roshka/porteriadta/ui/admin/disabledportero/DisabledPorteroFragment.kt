package com.roshka.porteriadta.ui.admin.disabledportero

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.roshka.porteriadta.databinding.FragmentDisabledPorteroBinding
import com.roshka.porteriadta.databinding.FragmentRegisterIncomeBinding
import com.roshka.porteriadta.ui.portero.RegisterIncomeViewModel
import com.roshka.porteriadta.ui.portero.recyclerView.SociosListAdapter

class DisabledPorteroFragment : Fragment() {
    private var _binding: FragmentDisabledPorteroBinding? = null
    lateinit var adapter: DisabledPorteroAdapterList
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = DisabledPorteroFragment()
    }

    private lateinit var viewModel: DisabledPorteroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[DisabledPorteroViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}