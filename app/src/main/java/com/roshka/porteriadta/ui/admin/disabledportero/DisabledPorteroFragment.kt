package com.roshka.porteriadta.ui.admin.disabledportero

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roshka.porteriadta.R

class DisabledPorteroFragment : Fragment() {

    companion object {
        fun newInstance() = DisabledPorteroFragment()
    }

    private lateinit var viewModel: DisabledPorteroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_disabled_portero, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DisabledPorteroViewModel::class.java)
        // TODO: Use the ViewModel
    }

}