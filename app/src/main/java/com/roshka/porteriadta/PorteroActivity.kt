package com.roshka.porteriadta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.roshka.porteriadta.ui.portero.PorteroFragment

class PorteroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portero)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PorteroFragment.newInstance())
                .commitNow()
        }
    }
}