package com.roshka.porteriadta.ui.admin.addmember
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.roshka.porteriadta.databinding.FragmentAddMemberBinding
import com.roshka.porteriadta.databinding.FragmentLoginBinding
import java.util.*

class AddMemberFragment : Fragment() {
    private var _binding: FragmentAddMemberBinding? = null
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = AddMemberFragment()
    }
    private lateinit var viewModel: AddMemberViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMemberBinding.inflate(inflater, container, false)
        val tipo = arrayOf("Socio","Gimnasio","Invitado","Guarderia")
        val spinner = binding.spinner
        spinner?.adapter = activity?.applicationContext?.let { ArrayAdapter(it, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tipo) } as SpinnerAdapter
        return binding.root
        }
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[AddMemberViewModel::class.java]
        // TODO: Use the ViewModel
        binding.aceptar.setOnClickListener {
            val nombre = binding.nombre.text.toString()
            val apellido = binding.lname.text.toString()
            val cedula = binding.cedula.text.toString()
            val nSocio = binding.nsocio.text.toString()
            val tipo: String = binding.spinner.selectedItem.toString()
            println(tipo)
            if (checkFields(nombre, apellido, cedula, nSocio, tipo)) {
                viewModel.setMember(apellido, nombre, cedula, nSocio, tipo)
                jobDone()
                clean()
            }
        }
    }

    fun checkFields(nombre:String,apellido : String , cedula : String, nsocio:String ,tipo : String): Boolean {
        if (nombre.isEmpty() ){
            binding.nombre.error="Campo Requerido"
            return false}
        if(apellido.isEmpty()){
            binding.lname.error="Campo Requerido"
                    return false }
        if(cedula.isEmpty()) {
            binding.cedula.error="Campo Requerido"
            return false
        }
        if(nsocio.isEmpty()) {
            binding.nsocio.error = "Campo Requerido"
            return false
        }
        if(tipo == "") {
            binding.nsocio.error="Campo Requerido"
            return false
        }

        return true
        }

    fun clean(){
        binding.nombre.setText("")
        binding.lname.setText("")
        binding.cedula.setText("")
        binding.nsocio.setText("")
    }
    fun jobDone(){
        Toast.makeText(activity,"Se agrego", Toast.LENGTH_SHORT).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}