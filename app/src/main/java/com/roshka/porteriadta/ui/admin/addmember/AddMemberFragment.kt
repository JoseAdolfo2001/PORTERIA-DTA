package com.roshka.porteriadta.ui.admin.addmember
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.roshka.porteriadta.data.Member
import com.roshka.porteriadta.databinding.FragmentAddMemberBinding
import com.roshka.porteriadta.databinding.FragmentLoginBinding
import com.roshka.porteriadta.network.FirebaseMemberDocument
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
        val tipo = arrayOf("Socio","Gimnasio","Invitado","Guarderia","Fiesta","Staff")
        val spinner = binding.spinner
        spinner?.adapter = activity?.applicationContext?.let { ArrayAdapter(it, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tipo) } as SpinnerAdapter
        return binding.root
        }
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[AddMemberViewModel::class.java]
        // TODO: Use the ViewModel
        binding.btnLogin.setOnClickListener {
            val nombre = binding.etName.text.toString()
            val apellido = binding.etLastname.text.toString()
            val cedula = binding.etCi.text.toString()
            val nSocio = binding.etNumSocio.text.toString()
            val tipo: String = binding.spinner.selectedItem.toString()
            if (checkFields(nombre, apellido, cedula)) {
                val member = Member(cedula)
                member.data[FirebaseMemberDocument.NAME] = nombre
                member.data[FirebaseMemberDocument.SURNAME] = apellido
                member.data[FirebaseMemberDocument.ID_MEMBER] = nSocio
                member.data[FirebaseMemberDocument.TYPE] = tipo
                viewModel.setMember(member)
                jobDone()
                clean()
            }
        }
    }

    fun checkFields(nombre:String,apellido : String , cedula : String): Boolean {
        if (nombre.isEmpty()) {
            binding.etName.error = "Campo Requerido"
            return false
        }
        if (apellido.isEmpty()) {
            binding.etLastname.error = "Campo Requerido"
            return false
        }
        if (cedula.isEmpty()) {
            binding.etCi.error = "Campo Requerido"
            return false
        }
        return true
    }

    fun clean(){
        binding.etName.setText("")
        binding.etLastname.setText("")
        binding.etCi.setText("")
        binding.etNumSocio.setText("")
        binding.spinner.getItemAtPosition(0)
    }
    fun jobDone(){
        Toast.makeText(activity,"Se agrego", Toast.LENGTH_SHORT).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}