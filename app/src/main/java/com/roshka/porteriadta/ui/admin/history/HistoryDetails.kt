package com.roshka.porteriadta.ui.admin.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.roshka.porteriadta.AdminActivity
import com.roshka.porteriadta.R
import com.roshka.porteriadta.data.Record
import com.roshka.porteriadta.databinding.FragmentHistoryDetailsBinding
import com.roshka.porteriadta.databinding.FragmentHistoryRecordBinding
import com.roshka.porteriadta.network.FirebaseRecordDocument
import com.roshka.porteriadta.ui.admin.history.RecyclerView.RecordAdapter
import com.roshka.porteriadta.ui.portero.MembersAdapter
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


class HistoryDetails : Fragment(),AdminActivity.IOnBackPressed{
    private lateinit var viewModel: HistoryRecordViewModel
    private var _binding: FragmentHistoryDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHistoryDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val dataholder: Record? = requireArguments().getSerializable("objeto") as Record?
        println(dataholder)
        if(dataholder != null){
            binding.tvNameSocio.text = dataholder.data[FirebaseRecordDocument.NAME_MEMBER].toString() +" "+ dataholder.data[FirebaseRecordDocument.SURNAME_MEMBER].toString()
            binding.tvCiSocio.text = "Cedula: " + dataholder.data[FirebaseRecordDocument.CI_MEMBER]
            if (dataholder.data[FirebaseRecordDocument.ID_MEMBER] != null) {
                binding.tvNumeroSocio.text = "Nº Socio: " + dataholder.data[FirebaseRecordDocument.ID_MEMBER].toString()
            } else {
                binding.tvNumeroSocio.text = "Nº Socio: "
            }
            binding.tvTipoSocio.text = "Tipo:"+ dataholder.data[FirebaseRecordDocument.TYPE].toString()
            if(dataholder.data[FirebaseRecordDocument.IS_EXIT] == true){
                binding.tvEsSalida.text = "SALIO"
            }else  binding.tvEsSalida.text = "ENTRO"
            if(dataholder.data[FirebaseRecordDocument.IS_WALK] == true){
                binding.tvCaminando.text = "CAMINANDO"
            }else binding.tvCaminando.text = "AUTO"

            if (dataholder.data[FirebaseRecordDocument.IS_DEFAULTER] == true){
                binding.tvMoroso.text = "MOROSO"
            }else binding.tvMoroso.text = "AL DIA"
            val formated = SimpleDateFormat("yyyy/MM/dd/HH-mm-ss", Locale.getDefault())
            var fecha = Date()
            fecha.time = dataholder.data[FirebaseRecordDocument.DATE_TIME].toString().toLong()
            var dateEnd = formated.format(fecha.time)

            binding.tvFecha.text = "Fecha y hora: $dateEnd"
            binding.tvNombreCompletoPortero.text = dataholder.data[FirebaseRecordDocument.NAME_PORTERO].toString() +" "+ dataholder.data[FirebaseRecordDocument.SURNAME_PORTERO].toString()
            binding.tvCedulaPortero.text = "Cedula: " + dataholder.data[FirebaseRecordDocument.CI_PORTERO].toString()
            binding.tvCorreoPortero.text = "Email: "+dataholder.data[FirebaseRecordDocument.EMAIL_PORTERO].toString()
            try {
                Picasso.get().load(dataholder.data[FirebaseRecordDocument.PHOTO].toString()).error(R.drawable.ic_baseline_insert_photo_24).into(binding.ivPhotoEntrada)
            } catch (e: Exception) {
                binding.ivPhotoEntrada.setImageResource(R.drawable.ic_baseline_insert_photo_24)
            }
        }



        return root
    }

    override fun onBackPressed(): Boolean {
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frame_layout,HistoryRecordFragment()).commit()
        return true
    }

}