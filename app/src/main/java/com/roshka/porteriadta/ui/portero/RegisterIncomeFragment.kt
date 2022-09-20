package com.roshka.porteriadta.ui.portero

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.hardware.Camera.CameraInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.Surface
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
import com.roshka.porteriadta.databinding.FragmentRegisterIncomeBinding
import com.roshka.porteriadta.ui.portero.recyclerView.SociosListAdapter
import java.util.*

class RegisterIncomeFragment : Fragment() {
    lateinit var adapter:SociosListAdapter
    private lateinit var binding: FragmentRegisterIncomeBinding
    private lateinit var viewModel: RegisterIncomeViewModel
    var foto: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = com.roshka.porteriadta.databinding.FragmentRegisterIncomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this)[RegisterIncomeViewModel::class.java]
        viewModel.getListMembers()
        binding.btnEnviar.setOnClickListener {
            viewModel.uploadImages(binding.ivFoto,requireActivity(),foto!!)
            binding.btnPrueba.setOnClickListener {
                viewModel.registrer()
            }
        }
        binding.btnCamara.setOnClickListener {
            abreCamara()
        }
        binding.ivCloseCardView.setOnClickListener {
            resetDate()
        }
        binding.btnCamara.setOnClickListener {
            abreCamara()
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
//    fun upLoadImage(){
//        val progressDialog = ProgressDialog(activity)
//        progressDialog.setMessage("Uploading file")
//        progressDialog.setCancelable(false)
//        progressDialog.show()
//        val formated = SimpleDateFormat("yyyy/MM/dd/HH-mm-ss", Locale.getDefault())
//        val now = Date()
//        val fileName = formated.format(now)
//        val storageReference = FirebaseStorage.getInstance().getReference("images/${fileName}")
//        storageReference.putFile(foto!!).addOnSuccessListener {
//            binding.ivFoto.setImageURI(null)
//            Toast.makeText(activity,"Se cargo correctamente",Toast.LENGTH_SHORT).show()
//            if(progressDialog.isShowing) progressDialog.dismiss()
//        }.addOnFailureListener{
//            if(progressDialog.isShowing) progressDialog.dismiss()
//            Toast.makeText(activity,"No se cargo correctamente",Toast.LENGTH_SHORT).show()
//        }
 //   }
    override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray,
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
    fun setCameraDisplayOrientation(
        activity: Activity,
        cameraId: Int, camera: Camera,
    ) {
        val info = CameraInfo()
        Camera.getCameraInfo(cameraId, info)
        val rotation = activity.windowManager.defaultDisplay
            .rotation
        var degrees = 0
        when (rotation) {
            Surface.ROTATION_0 -> degrees = 0
            Surface.ROTATION_90 -> degrees = 90
            Surface.ROTATION_180 -> degrees = 180
            Surface.ROTATION_270 -> degrees = 270
        }
        var result: Int
        if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360
            result = (360 - result) % 360 // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360
        }
        camera.setDisplayOrientation(result)
    }

}
