package com.roshka.porteriadta.ui.portero

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.roshka.porteriadta.R
import com.roshka.porteriadta.databinding.FragmentPorteroBinding
import com.roshka.porteriadta.ui.portero.recyclerView.SociosListAdapter

class PorteroFragment : Fragment() {
    lateinit var adapter:SociosListAdapter
    private lateinit var binding: com.roshka.porteriadta.databinding.FragmentPorteroBinding
    private lateinit var viewModel: PorteroViewModel
    var foto: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = com.roshka.porteriadta.databinding.FragmentPorteroBinding.inflate(inflater, container, false)
        return binding.root
            }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[PorteroViewModel::class.java]
        viewModel.getListMembers()
        binding.btnCamara.setOnClickListener {
            abreCamara()
        }
        binding.ivCloseCardView.setOnClickListener {
            resetDate()
        }
        binding.btnEnviar.setOnClickListener {
            resetDate()
            binding.ivFoto.setImageResource(R.drawable.icono_imagen)
        }
        viewModel.arrayMembers.observe(viewLifecycleOwner, Observer {
            println(it)
            adapter = SociosListAdapter(it,
                binding.rvMembers,
                binding.cardView,
                binding.tvNombre,
                binding.tvApellido,
                binding.tvCedula,
                binding.tvSociosNumeros,
                binding.ivFoto,
                binding.btnCamara,
                binding.btnEnviar,
                binding.tvDato,
                binding.searchView)
            binding.rvMembers.layoutManager = LinearLayoutManager(activity)
            binding.rvMembers.adapter = adapter
            val decoration =
                DividerItemDecoration(activity, LinearLayoutManager(activity).orientation)
            binding.rvMembers.addItemDecoration(decoration)

        })

    }
    fun resetDate(){
        binding.rvMembers.visibility = View.VISIBLE
        binding.tvDato.text = "Introduce los datos"
        binding.searchView.visibility = View.VISIBLE
        binding.searchView.visibility = View.VISIBLE
        binding.btnEnviar.visibility = View.GONE
        binding.ivFoto.visibility = View.GONE
        binding.cardView.visibility = View.GONE
    }
    private fun abreCamara(){
        val value = ContentValues() //  Crea un espacio de memoria vacia
        value.put(MediaStore.Images.Media.TITLE,"Nueva imagen")
        foto = requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,value)
        val camaraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT,foto)
        startActivityForResult(camaraIntent,viewModel.REQUEST_CAMERA)

    }

    private fun permisosCamara() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(requireContext(),Manifest.permission.CAMERA) ==
                PermissionChecker.PERMISSION_DENIED ||
                checkSelfPermission(requireContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PermissionChecker.PERMISSION_DENIED ){
                val permissionCamera = arrayOf(Manifest.permission.CAMERA ,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permissionCamera,viewModel.REQUEST_CAMERA)
            }
            else abreCamara()
        }
        else abreCamara()
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            viewModel.REQUEST_CAMERA -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {permisosCamara()}
                else Toast.makeText(activity , "No se pudo acceder a la camara" , Toast.LENGTH_SHORT)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == viewModel.REQUEST_CAMERA){
            binding.ivFoto.setImageURI(foto)
        }
    }
}
