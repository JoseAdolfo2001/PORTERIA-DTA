package com.roshka.porteriadta.ui.recovery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.roshka.porteriadta.PorteroActivity
import com.roshka.porteriadta.R
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

        binding.btnRecovery.setOnClickListener {
            Toast.makeText(activity, "Recovery Success", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_recoveryDialog_to_loginFragment)
        }

        binding.btnCancel.setOnClickListener {
            Toast.makeText(activity, "Canceled Recovery", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_recoveryDialog_to_loginFragment)
        }

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[RecoveryViewModel::class.java]
        // TODO: Use the ViewModel
    }
}