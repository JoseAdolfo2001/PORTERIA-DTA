package com.roshka.porteriadta.ui.recovery
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.roshka.porteriadta.databinding.DialogRecoveryBinding
import com.roshka.porteriadta.databinding.FragmentLoginBinding
import com.roshka.porteriadta.ui.portero.PorteroFragment
import com.roshka.porteriadta.ui.portero.PorteroViewModel

class RecoveryDialog : DialogFragment() {
    private lateinit var binding: DialogRecoveryBinding
    companion object {
        fun newInstance() = RecoveryDialog()
    }

    private lateinit var viewModel: RecoveryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogRecoveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Recuperar")
                .setPositiveButton("ACEPTAR",
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(activity, "Aceptar", Toast.LENGTH_SHORT).show()
                    })
                .setNegativeButton("CANCELAR",
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(activity, "Cancelar", Toast.LENGTH_SHORT).show()
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[RecoveryViewModel::class.java]
        // TODO: Use the ViewModel
    }
}