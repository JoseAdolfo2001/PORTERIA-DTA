package com.roshka.porteriadta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.alertdialogsample.CustomDialog
import com.roshka.porteriadta.ui.admin.AdminFragment

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AdminFragment.newInstance())
                .commitNow()
        }
        fun showAlert(view: View) {
//        MyDialog().show(supportFragmentManager, "mydialog")
            CustomDialog().show(supportFragmentManager, "customdialog")
        }
    }
}