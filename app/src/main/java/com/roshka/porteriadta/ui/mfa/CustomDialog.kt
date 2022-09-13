package com.example.alertdialogsample

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.roshka.porteriadta.R
import java.lang.IllegalStateException

class CustomDialog: DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val alertDialog = AlertDialog.Builder(it)
            alertDialog.setView(requireActivity().layoutInflater.inflate(R.layout.dialog_main, null))
            alertDialog.setPositiveButton("Submit", DialogInterface.OnClickListener { dialog, id ->

            })

            alertDialog.create()
        }?:throw IllegalStateException("Activity is null !!")
    }
}