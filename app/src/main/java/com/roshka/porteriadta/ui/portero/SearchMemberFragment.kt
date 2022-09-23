package com.roshka.porteriadta.ui.portero

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.roshka.porteriadta.PorteroActivity
import com.roshka.porteriadta.R
import com.roshka.porteriadta.databinding.FragmentSearchMemberBinding


class SearchMemberFragment : DialogFragment() {
    private lateinit var binding: FragmentSearchMemberBinding

    companion object {
        fun newInstance() = SearchMemberFragment()
    }

    private val viewModel: PorteroActivityViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        this.binding = FragmentSearchMemberBinding.inflate(inflater, container, false)

        binding.rwMembers.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.arrayMembers.observe(viewLifecycleOwner) {
            println("EBSERVER ARRAY")
            val adapter = MembersAdapter(it) { position -> onListItemClick(position) }
            binding.rwMembers.adapter = adapter
        }

        viewModel.error.observe(viewLifecycleOwner) {
            println("OBSERVER ERROR")
            showAlert(it)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_item, menu)
        val searchView =
            SearchView((context as PorteroActivity).supportActionBar?.themedContext ?: context)
        menu.findItem(R.id.search_action).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }
        searchView.isIconified = false
        searchView.isFocusable = true
        searchView.requestFocusFromTouch()

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
        builder.setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun onListItemClick(position: Int) {
        viewModel.updateAddMember(position)
        hide()
        activity?.onBackPressed()
    }

    fun hide() {
        val vieww = activity?.currentFocus
        if (vieww != null) {
            //Aquí esta la magia
            val input: InputMethodManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            input.hideSoftInputFromWindow(vieww.windowToken, 0)
        }
    }
}