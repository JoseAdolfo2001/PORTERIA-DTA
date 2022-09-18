package com.roshka.porteriadta

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationView
import com.roshka.porteriadta.databinding.ActivityPorteroBinding

class PorteroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPorteroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPorteroBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}