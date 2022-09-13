package com.example.alertdialogsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.roshka.porteriadta.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
    }

    fun showAlert(view: View) {
//        MyDialog().show(supportFragmentManager, "mydialog")
        CustomDialog().show(supportFragmentManager, "customdialog")
    }
}