package com.roshka.porteriadta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.navigation.findNavController
import com.roshka.porteriadta.ui.admin.AdminFragment
import com.roshka.porteriadta.ui.updatePass.UpdatePass

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

           supportFragmentManager.beginTransaction()
                .replace(R.id.putoooooo, UpdatePass.newInstance())
                .commitNow()
        }



//        fun showAlert(view: View) {
////        MyDialog().show(supportFragmentManager, "mydialog")
//            DialogUpdatePass().show(supportFragmentManager, "customdialog")
//        }
    }
