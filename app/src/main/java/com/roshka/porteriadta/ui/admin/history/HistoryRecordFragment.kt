package com.roshka.porteriadta.ui.admin.history

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.text.format.Time
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.roshka.porteriadta.data.FilterParameters
import com.roshka.porteriadta.data.Record
import com.roshka.porteriadta.databinding.FragmentHistoryRecordBinding
import com.roshka.porteriadta.ui.admin.history.RecyclerView.RecordAdapter
import java.util.*


class HistoryRecordFragment : Fragment() {
    private var _binding: FragmentHistoryRecordBinding? = null
    var array = listOf<Record>()
    lateinit var adapter: RecordAdapter
    var timeInMilliSeconds1 = 0L
    var timeInMilliSeconds2 = 0L


    private val binding get() = _binding!!
    private lateinit var viewModel: HistoryRecordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHistoryRecordBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this)[HistoryRecordViewModel::class.java]
        binding.btnFechaInicial.setOnClickListener {
            val fecha1 = DatePickerFragment { year, month, day ->
                binding.etFechaIniciial.setText(mostrarResultado(year, month, day))
                val time = Time()
                time.set(day, month - 1, year)
                timeInMilliSeconds1 = time.toMillis(true)
            }
            fecha1.show(requireActivity().supportFragmentManager, "DataPicker")


        }
        binding.btnFechaFinal.setOnClickListener {
            val fecha2 = DatePickerFragment { year, month, day ->
                binding.etFechaFinal.setText(mostrarResultado(year, month, day))
                val time = Time()
                time.set(day, month - 1, year)
                println(time)
                timeInMilliSeconds2 = time.toMillis(true)
                println(timeInMilliSeconds2)
            }
            fecha2.show(requireActivity().supportFragmentManager, "DataPicker")
        }
        viewModel.arrayRecords.observe(viewLifecycleOwner, Observer {
            array = it
            adapter = RecordAdapter(it, binding.etFechaIniciial.text.toString())
            binding.rvRecord.layoutManager = LinearLayoutManager(activity)
            binding.rvRecord.adapter = adapter
            val decoration =
                DividerItemDecoration(activity, LinearLayoutManager(activity).orientation)
            binding.rvRecord.addItemDecoration(decoration)

        })
        binding.btnFiltrar.setOnClickListener {
            viewModel.onQueryTextChange(FilterParameters(timeInMilliSeconds1, timeInMilliSeconds2))
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun mostrarResultado(year: Int, month: Int, day: Int): String {
        val string = "${day}/${month}/${year}"
        return string
    }

    class DatePickerFragment(val listener: (year: Int, month: Int, day: Int) -> Unit) :
        DialogFragment(), DatePickerDialog.OnDateSetListener {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            return DatePickerDialog(requireActivity(), this, year, month, day)
        }

        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
            listener(year, month + 1, day)
        }

    }


}