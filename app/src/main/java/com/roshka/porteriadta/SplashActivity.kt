package com.roshka.porteriadta

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.roshka.porteriadta.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var fb: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fb = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()

        handler = Handler()
        handler!!.postDelayed({
            val user = auth.currentUser
            if (user != null) {
                fb.collection("Users").document(user.email.toString()).get()
                    .addOnSuccessListener {
                        val type = it.get("Nivel").toString()
                        if (type == "admin") {
                            val intent = Intent(this, AdminActivity::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(this, PorteroActivity::class.java)
                            startActivity(intent)
                        }
                    }
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }, 1000)
    }
}