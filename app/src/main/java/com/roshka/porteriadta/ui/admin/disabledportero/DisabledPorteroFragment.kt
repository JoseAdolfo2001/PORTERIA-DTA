package com.roshka.porteriadta.ui.admin.disabledportero

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.roshka.porteriadta.AdminActivity
import com.roshka.porteriadta.R
import com.roshka.porteriadta.databinding.FragmentDisabledPorteroBinding

class DisabledPorteroFragment : Fragment() {
    private lateinit var  binding: FragmentDisabledPorteroBinding
    lateinit var adapter: DisabledPorteroAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.


    companion object {
        fun newInstance() = DisabledPorteroFragment()
    }

    private lateinit var viewModel: DisabledPorteroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        this.binding = FragmentDisabledPorteroBinding.inflate(inflater, container, false)
        binding.rwPortero.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this)[DisabledPorteroViewModel::class.java]

        viewModel.arrayUsers.observe(viewLifecycleOwner) {
            val adapter = activity?.let { it1 -> DisabledPorteroAdapter(it, it1) }
            binding.rwPortero.adapter = adapter
        }
        viewModel.error.observe(viewLifecycleOwner) {
            showAlert(it)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_item, menu)
        val searchView = SearchView((context as AdminActivity).supportActionBar?.themedContext ?: context)
        menu.findItem(R.id.search_action).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return viewModel.onQueryTextChange(query)
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return viewModel.onQueryTextChange(newText)
            }
        })
        searchView.setOnClickListener { }
    }

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar",null)
        val dialog = builder.create()
        dialog.show()
    }
}