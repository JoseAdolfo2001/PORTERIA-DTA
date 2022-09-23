package com.roshka.porteriadta.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.roshka.porteriadta.AdminActivity
import com.roshka.porteriadta.PorteroActivity
import com.roshka.porteriadta.R
import com.roshka.porteriadta.data.User
import com.roshka.porteriadta.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.tvRecoverPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recoveryDialog)
        }

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.btnLogin.setOnClickListener {
            if (checkAllFields()) {
                it.visibility = View.GONE
                binding.loading.visibility = View.VISIBLE
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()
                viewModel.loginUsers(User(email, password, mutableMapOf()))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.isSuccessfulLogin.observe(viewLifecycleOwner) {
            if (it == viewModel.ADMIN) {
                val intent = Intent(activity, AdminActivity::class.java)
                requireActivity().startActivity(intent)
            } else {
                val intent = Intent(activity, PorteroActivity::class.java)
                requireActivity().startActivity(intent)
            }
        }

        viewModel.errorLogin.observe(viewLifecycleOwner) {
            binding.btnLogin.visibility = View.VISIBLE
            binding.loading.visibility = View.GONE
            clean()
            showAlert(it)
        }
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
        this.binding.etEmail.setText("")
        this.binding.etPassword.setText("")
    }

    private fun checkAllFields(): Boolean {
        val email = binding.etEmail.text.toString().trim()
        if (email.isEmpty()) {
            binding.etEmail.error = "Este campo es requerido"
            return false
        }

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (!(email.trim { it <= ' ' }.matches(emailPattern.toRegex()))) {
            binding.etEmail.error = "Tiene que ser un correo válido"
            return false
        }

        val password = binding.etPassword.text.toString().trim()
        if (password.isEmpty()) {
            binding.etPassword.error = "Este campo es requerido"
            return false
        }

        if (password.length < 6) {
            binding.etPassword.error = "Mínimo 6 caracteres"
            return false
        }
        // after all validation return true.
        return true
    }
}