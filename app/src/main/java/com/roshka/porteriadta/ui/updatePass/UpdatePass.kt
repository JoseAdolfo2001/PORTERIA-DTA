package com.roshka.porteriadta.ui.updatePass

import android.app.AlertDialog
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.roshka.porteriadta.LoginActivity
import com.roshka.porteriadta.databinding.FragmentUpdatePassBinding

class UpdatePass : Fragment() {

    companion object {
        fun newInstance() = UpdatePass()
    }
    private lateinit var binding: FragmentUpdatePassBinding
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
            if (checkAllFields(password,newPass,newPassC)) {
                it.visibility = View.GONE
                binding.carga.visibility = View.VISIBLE
                viewModel.changePass(password, newPass, newPassC)
            }
            viewModel.code.observe(viewLifecycleOwner, Observer {
                if (it == 3) {
                    binding.updateBtn.visibility = View.VISIBLE
                    binding.carga.visibility = View.GONE
                    currentPassWrong()
                }else if (it == 2){
                    binding.updateBtn.visibility = View.VISIBLE
                    binding.carga.visibility = View.GONE
                    updateDone()
                    val intent = Intent(activity,LoginActivity::class.java)
                    requireActivity().startActivity(intent)
                }
            })
        }
    }
    private fun checkAllFields(password : String,newPass:String,newPassC:String): Boolean {
        if (password.isEmpty() && newPass.isEmpty() && newPassC.isEmpty()) {
            binding.passEt.error ="Campo Requerido"
            binding.passNew.error="Campo Requerido"
            binding.passNewC.error="Campo Requerido"
            return false
        }
        if (newPass != newPassC ) {
             binding.passNew.error ="Contraseñas diferentes "
             binding.passNewC.error="Contraseñas diferentes "
             return false
        }
        if(password==newPass) {
            binding.passNew.error = "Contraseña actual igual a la nueva"
            return false
        }
        if(newPass.length<6){
            binding.passNew.error = "Contraseña tiene que ser mayor a 6 caracteres"
            binding.passNewC.error = "Contraseña tiene que ser mayor a 6 caracteres"
            return false
        }
           return true
        }
    private fun currentPassWrong() {
        binding.passEt.error = "Contraseña actual incorrecta"
    }
    private fun updateDone() {
        Toast.makeText(activity,"Se Actualizo",Toast.LENGTH_SHORT).show()
    }

}

