package com.roshka.porteriadta.ui.change.password

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.roshka.porteriadta.LoginActivity
import com.roshka.porteriadta.databinding.FragmentChangePasswordBinding

class ChangePasswordFragment : Fragment() {
    private var _binding: FragmentChangePasswordBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    companion object {
        fun newInstance() = ChangePasswordFragment()
    }

    private lateinit var viewModel: ChangePasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ChangePasswordViewModel::class.java]
        binding.updateBtn.setOnClickListener {
            val password = binding.passEt.text.toString().trim()
            val newPass = binding.passNew.text.toString().trim()
            val newPassC = binding.passNewC.text.toString().trim()
            if (checkAllFields(password, newPass, newPassC)) {
                it.visibility = View.GONE
                binding.cargar.visibility = View.VISIBLE
                viewModel.changePass(password, newPass)
            }
        }
        viewModel.isSuccessfulChange.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                binding.updateBtn.visibility = View.VISIBLE
                binding.cargar.visibility = View.GONE
                messageToast(it.message)
                val intent = Intent(activity, LoginActivity::class.java)
                requireActivity().startActivity(intent)
            } else {
                clean()
                binding.updateBtn.visibility = View.VISIBLE
                binding.cargar.visibility = View.GONE
                messageToast(it.message)
            }
        }
    }

    private fun checkAllFields(password: String, newPass: String, newPassC: String): Boolean {
        if (password.isEmpty()) {
            binding.passEt.error = "Campo Requerido"
            return false
        }

        if (newPass.isEmpty()) {
            binding.passNew.error = "Campo Requerido"
            return false
        }

        if (newPassC.isEmpty()) {
            binding.passNewC.error = "Campo Requerido"
            return false
        }

        if (newPass != newPassC) {
            binding.passNew.error = "Contraseñas diferentes"
            binding.passNewC.error = "Contraseñas diferentes"
            binding.passNew.setText("")
            binding.passNewC.setText("")
            return false
        }

        if (password == newPass) {
            binding.passNew.error = "Contraseña nueva igual a la actual"
            binding.passNew.setText("")
            return false
        }

        if (newPass.length < 6) {
            binding.passNew.error = "Contraseña tiene que ser mayor a 6 caracteres"
            binding.passNew.setText("")
            binding.passNewC.setText("")
            return false
        }

        return true
    }

    private fun messageToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun clean() {
        binding.passEt.setText("")
        binding.passNew.setText("")
        binding.passNewC.setText("")
    }
}
