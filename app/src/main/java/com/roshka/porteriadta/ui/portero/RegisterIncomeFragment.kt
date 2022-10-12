package com.roshka.porteriadta.ui.portero

import android.Manifest
import android.app.Activity
import android.app.DirectAction
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.roshka.porteriadta.R
import com.roshka.porteriadta.databinding.FragmentRegisterIncomeBinding

class RegisterIncomeFragment : Fragment() {
    private lateinit var binding: FragmentRegisterIncomeBinding
    private val viewModel: PorteroActivityViewModel by activityViewModels()
    private var foto: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRegisterIncomeBinding.inflate(inflater, container, false)

        binding.rvMembers.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        val decoration =
            DividerItemDecoration(activity, LinearLayoutManager(activity).orientation)
        binding.rvMembers.addItemDecoration(decoration)

        binding.checkBoxIsCar.setOnClickListener {
            viewModel.setIsExit(!binding.checkBoxIsCar.isChecked)
        }

        binding.checkBoxIsEntry.setOnClickListener {
            viewModel.setIsWalk(!binding.checkBoxIsEntry.isChecked)
        }

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

        binding.btnEnviar .setOnClickListener {
            viewModel.uploadImages(binding.ivFoto, requireActivity(), foto)
            foto = null
        }

        binding.viewFoto.setOnClickListener {
            binding.ivArrowQuit.visibility = View.VISIBLE
            binding.ivQuitImage.visibility = View.VISIBLE
            binding.viewFoto.visibility = View.VISIBLE
            binding.checkBox.visibility = View.GONE
            binding.rvMembers.visibility = View.GONE
            binding.ivFoto.visibility = View.VISIBLE
        }

        binding.ivQuitImage.setOnClickListener {
            binding.ivFoto.setImageResource(R.drawable.ic_baseline_insert_photo_24)
            foto = null
        }

        binding.navAddMember.setOnClickListener {
            findNavController().navigate(R.id.action_nav_register_income_to_addMemberFragment)
        }

        binding.ivArrowQuit.setOnClickListener {
            binding.ivArrowQuit.visibility = View.GONE
            binding.ivQuitImage.visibility = View.GONE
            binding.checkBox.visibility = View.VISIBLE
            binding.rvMembers.visibility = View.VISIBLE
            binding.ivFoto.visibility = View.GONE
        }

        binding.btnCamera.setOnClickListener {
            abreCamara()
        }

        viewModel.addMembers.observe(viewLifecycleOwner) {
            val adapter = MembersAdapter(it) { position -> onListItemClick(position) }
            binding.rvMembers.adapter = adapter
            val itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter))
            itemTouchHelper.attachToRecyclerView(binding.rvMembers)
        }

        viewModel.isSuccessful.observe(viewLifecycleOwner) {
            binding.btnEnviar.visibility = View.VISIBLE
            binding.loading.visibility = View.GONE
            if (it.isSuccessful) {
                Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
            } else {
                showAlert(it.message)
            }
        }
    }

//    override fun onStart() {
//        super.onStart()
//        var uri = foto
//        binding.ivFoto.setImageURI(uri)
//    }

    private fun abreCamara() {
        val value = ContentValues() //  Crea un espacio de memoria vacia
        value.put(MediaStore.Images.Media.TITLE, "Nueva imagen")
        foto = requireActivity().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            value
        )
        val camaraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, foto)
        startActivityForResult(camaraIntent, viewModel.REQUEST_CAMERA)

    }

    private fun permisosCamara() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(requireContext(), Manifest.permission.CAMERA) ==
                PermissionChecker.PERMISSION_DENIED ||
                checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PermissionChecker.PERMISSION_DENIED
            ) {
                val permissionCamera = arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                requestPermissions(permissionCamera, viewModel.REQUEST_CAMERA)
            } else abreCamara()
        } else abreCamara()
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            viewModel.REQUEST_CAMERA -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permisosCamara()
                } else Toast.makeText(
                    activity,
                    "No se pudo acceder a la camara",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == viewModel.REQUEST_CAMERA) {
            try {
                binding.ivFoto.setImageURI(foto)
            } catch (e: Exception) {
                Toast.makeText(activity, "Imagen nulo", Toast.LENGTH_SHORT).show()
            }

        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_nav, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.nav_search_members) {
            findNavController().navigate(R.id.action_nav_register_income_to_searchMemberFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onListItemClick(position: Int) {
    }

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
    }
    override fun onStart() {
        super.onStart()
        var uri = foto
        binding.ivFoto.setImageURI(uri)
    }

}