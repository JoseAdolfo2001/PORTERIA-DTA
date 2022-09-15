package com.roshka.porteriadta.ui.updatePass

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.roshka.porteriadta.LoginActivity
import com.roshka.porteriadta.databinding.FragmentUpdatePassBinding

class UpdatePass : Fragment() {

    companion object {
        fun newInstance() = UpdatePass()
    }
    lateinit var binding: FragmentUpdatePassBinding
    lateinit var viewModel: UpdatePassViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdatePassBinding.inflate(inflater, container, false)
        return binding.root
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[UpdatePassViewModel::class.java]
        binding.updateBtn.setOnClickListener {
            val password = binding.passEt.text.toString()
            val newPass = binding.passNew.text.toString()
            val newPassC = binding.passNewC.text.toString()
            if (password.isNotEmpty() && newPass.isNotEmpty() && newPassC.isNotEmpty()) {
                if (newPass == newPassC && newPass != password) {
                    viewModel.changePass(password, newPass, newPassC)
                }else{
                    contraDif()
                }
            }else {
                isEmpty()
            }
            viewModel.code.observe(viewLifecycleOwner, Observer {

                if (it == 1) {
                    testfunca()
                } else if (it == 3) {
                    currentPassWrong()
                }else if ( it == 2){
                    updateDone()
                    var intent = Intent(activity,LoginActivity::class.java)
                    requireActivity().startActivity(intent)
                }
            })
        }
    }



//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return activity?.let {
//            val builder = AlertDialog.Builder(it)
//
//            // Get the layout inflater
//            val inflater = requireActivity().layoutInflater;
//            // Inflate and set the layout for the dialog
//            // Pass null as the parent view because its going in the dialog layout
//            builder.setView(inflater.inflate(R.layout.dialog_update_pass, null))
//                // Add action buttons
//            builder.setPositiveButton("Actualizar", DialogInterface.OnClickListener { dialog, id ->
//                viewModel = ViewModelProvider(this)[UpdatePassViewModel::class.java]
//                val password = binding.passEt.text.toString()
//                val newPass = binding.passNew.text.toString()
//                val newPassC = binding.passNewC.text.toString()
//                if (password.isNotEmpty() || newPass.isNotEmpty() || newPassC.isNotEmpty()) {
//                    if (newPass == newPassC) {
//                        viewModel.changePass(password, newPass, newPassC)
//                    }
//                }else {
//                    showAlertEmpyText()
//                }
//                viewModel.code.observe(viewLifecycleOwner, Observer {
//                    if (it == 2) {
//                        testfunca()
//                    } else if (it == 4) {
//                        showAlertInvalidLogin()
//                    }
//                })
//
//            })
//            builder.setNegativeButton("Cancelar", DialogInterface.OnClickListener({dialog, id ->
//                getDialog()?.cancel()
//
//            }))
//            builder.create()
//        } ?: throw IllegalStateException("Activity cannot be null")
//    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this)[UpdatePassViewModel::class.java]
//        binding.updateBtn.setOnClickListener {
//            val password = binding.passEt.text.toString()
//            val newPass = binding.passNew.text.toString()
//            val newPassC = binding.passNewC.text.toString()
//            viewModel.changePass(password, newPass, newPassC)
//            if (password.isNotEmpty() || newPass.isNotEmpty() || newPassC.isNotEmpty()) {
//                if (newPass == newPassC) {
//
//                }
//            }
//            viewModel.code.observe(viewLifecycleOwner, Observer {
//                if (it == 2) {
//                    testfunca()
//                } else if (it == 4) {
//                    showAlertInvalidLogin()
//                }
//            })
//        }
//    }

    private fun contraDif() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage("Las Contraseñas son diferentes ")
        builder.setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun testfunca() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Funciona")
        builder.setMessage("funca kp")
        builder.setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun isEmpty() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage("Campo Vacio")
        builder.setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
    }
    private fun currentPassWrong() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage("Contraseña actual incorrecta")
        builder.setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
    }
    private fun updateDone() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Se actualizo la Contraseña")
        builder.setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
    }

}
