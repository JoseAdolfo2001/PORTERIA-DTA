package com.roshka.porteriadta.ui.admin.addportero

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roshka.porteriadta.R

class AddPorteroFragment : Fragment() {

    companion object {
        fun newInstance() = AddPorteroFragment()
    }

    private lateinit var viewModel: AddPorteroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_portero, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddPorteroViewModel::class.java)
        // TODO: Use the ViewModel
    }

}