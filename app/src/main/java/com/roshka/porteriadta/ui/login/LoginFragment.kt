package com.roshka.porteriadta.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.roshka.porteriadta.AdminActivity
import com.roshka.porteriadta.PorteroActivity
import com.roshka.porteriadta.R
import com.roshka.porteriadta.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
     var users = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        this.binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        println(viewModel.user)
        super.onActivityCreated(savedInstanceState)
        var user = FirebaseAuth.getInstance().currentUser

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.loginUsers(email, password)

        }

//        viewModel.flag.observe(viewLifecycleOwner, Observer{
//            Toast.makeText(activity,"Entre kp",Toast.LENGTH_SHORT).show()
//            if(it){
//
//                var intent = Intent(activity , AdminActivity::class.java)
//                requireActivity().startActivity(intent)
//            }else {
//                var intent = Intent(activity,PorteroActivity::class.java)
//                requireActivity().startActivity(intent)}
//
//        })

        viewModel.code.observe(viewLifecycleOwner, Observer{
            if(it == 1){
                showAlertEmpyText()
            }
            else if(it == 4){
                showAlertInvalidLogin()
            }
        })


    }

    override fun onStart() {
        super.onStart()
        viewModel.flag.observe(viewLifecycleOwner, Observer{
            println(it)
            println(viewModel.user)
            Toast.makeText(activity,"Entre kp",Toast.LENGTH_SHORT).show()
            val prueba = viewModel.user != null
            if(it == true || prueba){

                var intent = Intent(activity , AdminActivity::class.java)
                requireActivity().startActivity(intent)
            }else if (it == false || viewModel.user != null){
                var intent = Intent(activity,PorteroActivity::class.java)
                requireActivity().startActivity(intent)}
            var intent = Intent(activity , AdminActivity::class.java)

        })




    }

    private fun showAlertEmpyText() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage("Introduzca email y contraseña")
        builder.setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
}
    private fun showAlertInvalidLogin() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage("Correo o contraseña, no valido")
        builder.setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
    }
}