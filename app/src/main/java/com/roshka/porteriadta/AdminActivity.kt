package com.roshka.porteriadta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.roshka.porteriadta.databinding.ActivityAdminBinding
import com.roshka.porteriadta.databinding.ActivityLoginBinding
import com.roshka.porteriadta.ui.admin.AdminFragment

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}