package com.roshka.porteriadta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

import com.google.firebase.firestore.FirebaseFirestore
import com.roshka.porteriadta.ui.login.LoginFragment
import com.roshka.porteriadta.ui.login.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    val fb = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.user != null) {
            fb.collection("Users").document(viewModel.user!!.email.toString()).get()
                .addOnSuccessListener {
                    val type= it.get("Nivel").toString()
                    if (type == "admin") {
                        val intent = Intent(this, AdminActivity::class.java)
                        startActivity(intent)
                    } else {
                        val intent = Intent(this, PorteroActivity::class.java)
                        startActivity(intent)
                    }
                }
        }
    }
}
