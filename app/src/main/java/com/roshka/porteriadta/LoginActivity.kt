package com.roshka.porteriadta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.roshka.porteriadta.ui.login.LoginFragment

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
            var button = findViewById<Button>(R.id.btn)
            var texto1 = findViewById<EditText>(R.id.et1)
            var texto2 = findViewById<EditText>(R.id.et2)

            button.setOnClickListener {
                if(texto1.text.isNotEmpty() && texto2.text.isNotEmpty()){
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        texto1.text.toString() , texto2.text.toString()).addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(this,"Hola me registre kp", Toast.LENGTH_SHORT).show()}
                        else Toast.makeText(this,"fallo kp", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        }
    }
