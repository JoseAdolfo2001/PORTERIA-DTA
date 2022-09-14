package com.example.alertdialogsample

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View.inflate
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.resources.Compatibility.Api21Impl.inflate
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.roshka.porteriadta.R
import com.roshka.porteriadta.databinding.ActivityAdminBinding.inflate
import com.roshka.porteriadta.databinding.DialogMainBinding
import com.roshka.porteriadta.ui.updatePass.ViewModelPass
import java.lang.IllegalStateException

class CustomDialog: DialogFragment(){
        // This property is only valid between onCreateDialog and
        // onDestroyView.

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            _binding = DialogExampleBinding.inflate(LayoutInflater.from(context))
            return AlertDialog.Builder(requireActivity())
                .setView(binding.root)
                .create()
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }
