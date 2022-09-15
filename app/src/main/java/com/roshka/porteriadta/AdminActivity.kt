package com.roshka.porteriadta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.roshka.porteriadta.databinding.ActivityAdminBinding
import com.roshka.porteriadta.databinding.ActivityLoginBinding
import com.roshka.porteriadta.ui.admin.AdminFragment
import com.roshka.porteriadta.ui.admin.AdminViewModel

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}