package com.roshka.porteriadta.ui.admin.addportero

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.roshka.porteriadta.data.User
import com.roshka.porteriadta.databinding.FragmentAddPorteroBinding
import com.roshka.porteriadta.network.FirebaseUsersDocument

class AddPorteroFragment : Fragment() {
    private var _binding: FragmentAddPorteroBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = AddPorteroFragment()
    }

    private lateinit var viewModel: AddPorteroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPorteroBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[AddPorteroViewModel::class.java]

        binding.btnRegistrar.setOnClickListener {
            if (checkAllFields()) {
                it.visibility = View.GONE
                binding.loading.visibility = View.VISIBLE
                val name = binding.portName.text.toString().trim()
                val surname = binding.portSurname.text.toString().trim()
                val email = binding.portEmail.text.toString().trim()
                val password = binding.portPassword.text.toString().trim()
                val ci = binding.portCi.text.toString().trim()
                val user = User(email, password)
                user.data[FirebaseUsersDocument.NAME] = "$name $surname"
                user.data[FirebaseUsersDocument.CI] = ci
                viewModel.addPortero(user)
            }
        }

        viewModel.isSuccessful.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                clean()
            } else {
                showAlert(it.message)
            }
            binding.btnRegistrar.visibility = View.VISIBLE
            binding.loading.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkAllFields(): Boolean {
        val name = binding.portName.text.toString().trim()
        if (name.isEmpty()) {
            binding.portName.error = "Este campo es requerido"
            return false
        }

        val surname = binding.portSurname.text.toString().trim()
        if (surname.isEmpty()) {
            binding.portSurname.error = "Este campo es requerido"
            return false
        }

        val ci = binding.portCi.text.toString().trim()
        if (ci.isEmpty()) {
            binding.portCi.error = "Este campo es requerido"
            return false
        }

        val email = binding.portEmail.text.toString().trim()
        if (email.isEmpty()) {
            binding.portEmail.error = "Este campo es requerido"
            return false
        }

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (!(email.trim { it <= ' ' }.matches(emailPattern.toRegex()))) {
            binding.portEmail.error = "Tiene que ser un correo válido"
            return false
        }

        val password = binding.portPassword.text.toString().trim()
        if (password.isEmpty()) {
            binding.portPassword.error = "Este campo es requerido"
            return false
        }

        if (password.length < 6) {
            binding.portPassword.error = "Mínimo 6 caracteres"
            return false
        }
        // after all validation return true.
        return true
    }

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun clean() {
        this.binding.portName.setText("")
        this.binding.portSurname.setText("")
        this.binding.portCi.setText("")
        this.binding.portEmail.setText("")
        this.binding.portPassword.setText("")
    }

}