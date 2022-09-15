package com.roshka.porteriadta.ui.recovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.roshka.porteriadta.databinding.DialogRecoveryBinding

class RecoveryDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogRecoveryBinding

    companion object {
        fun newInstance() = RecoveryDialog()
    }

    private lateinit var viewModel: RecoveryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogRecoveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[RecoveryViewModel::class.java]
        // TODO: Use the ViewModel
        binding.btnRecovery.setOnClickListener {
            if (checkAllFields()) {
                viewModel.resetPassword(binding.etRecoveryEmail.text.toString())
            } else {
                clean()
            }
        }

        viewModel.isSuccessfulRecovery.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                this.dismiss()
            } else {
                Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                clean()
            }
        }
    }

    private fun checkAllFields(): Boolean {
        val email = binding.etRecoveryEmail.text.toString()
        if (email.isEmpty()) {
            binding.etRecoveryEmail.error = "Este campo es requerido"
            return false
        }

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (!(email.trim { it <= ' ' }.matches(emailPattern.toRegex()))) {
            binding.etRecoveryEmail.error = "Tiene que ser un correo válido"
            return false
        }
        // after all validation return true.
        return true
    }

    private fun clean () {
        binding.etRecoveryEmail.setText("")
    }
}