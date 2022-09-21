package com.roshka.porteriadta.ui.admin.addmember

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.roshka.porteriadta.PorteroActivity
import com.roshka.porteriadta.R
import com.roshka.porteriadta.data.Member
import com.roshka.porteriadta.databinding.FragmentAddMemberBinding
import com.roshka.porteriadta.network.FirebaseMemberDocument

class AddMemberFragment : Fragment() {
    private var _binding: FragmentAddMemberBinding? = null
    private val binding get() = _binding!!

    companion object {
    }

    private lateinit var viewModel: AddMemberViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMemberBinding.inflate(inflater, container, false)
        val tipo = arrayOf("Socio", "Gimnasio", "Invitado", "Guarderia", "Fiesta", "Staff","Restaurante")
        val spinner = binding.spinner
        spinner.adapter = activity?.applicationContext?.let {
            ArrayAdapter(
                it,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                tipo
            )
        } as SpinnerAdapter
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[AddMemberViewModel::class.java]

        binding.btnLogin.setOnClickListener {
            val nombre = binding.etName.text.toString().trim()
            val apellido = binding.etLastname.text.toString().trim()
            val cedula = binding.etCi.text.toString().trim()
            val nSocio = binding.etNumSocio.text.toString().trim()
            val tipo: String = binding.spinner.selectedItem.toString().trim()
            if (checkFields(nombre, apellido, cedula)) {
                val member = Member(cedula)
                member.data[FirebaseMemberDocument.NAME] = nombre
                member.data[FirebaseMemberDocument.SURNAME] = apellido
                member.data[FirebaseMemberDocument.ID_MEMBER] = nSocio
                member.data[FirebaseMemberDocument.TYPE] = tipo
                viewModel.setMember(member)
                if(activity is PorteroActivity) {
                    findNavController().navigate(R.id.action_addMemberFragment_to_nav_register_income)
                }
            }
        }
        viewModel.isSuccessful.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                clean()
            } else {
                showAlert(it.message)
            }
        }
    }

    fun checkFields(nombre: String, apellido: String, cedula: String): Boolean {
        if (nombre.isEmpty()) {
            binding.etName.error = "Campo Requerido"
            return false
        }
        if (apellido.isEmpty()) {
            binding.etLastname.error = "Campo Requerido"
            return false
        }
        if (cedula.isEmpty()) {
            binding.etCi.error = "Campo Requerido"
            return false
        }
        return true
    }

    fun clean() {
        binding.etName.setText("")
        binding.etLastname.setText("")
        binding.etCi.setText("")
        binding.etNumSocio.setText("")
        binding.spinner.getItemAtPosition(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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