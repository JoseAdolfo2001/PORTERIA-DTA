package com.roshka.porteriadta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.roshka.porteriadta.databinding.ActivityLoginBinding
import com.roshka.porteriadta.databinding.ActivityPorteroBinding
import com.roshka.porteriadta.ui.portero.PorteroFragment

class PorteroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPorteroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPorteroBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}