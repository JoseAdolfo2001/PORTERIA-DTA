package com.roshka.porteriadta

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.roshka.porteriadta.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var auth: FirebaseAuth
    private lateinit var fb: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        fb = FirebaseFirestore.getInstance()

        setSupportActionBar(binding.appBarMain.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_admin)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_history,
                R.id.nav_add_portero,
                R.id.nav_disabled_portero,
                R.id.nav_change_password,
                R.id.nav_logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val headerView = binding.navView.getHeaderView(0)

        val user = auth.currentUser

        if (user != null) {
            fb.collection("Users").document(user.email.toString()).get()
                .addOnSuccessListener {
                    val name = it.get("Nombre").toString()
                    val nameUser = headerView.findViewById<TextView>(R.id.name_user)
                    nameUser.text = name
                }
        }

//        val nameUser = headerView.findViewById<TextView>(R.id.name_user)
//        nameUser.text = user?.displayName ?: ""

        val emailUser = headerView.findViewById<TextView>(R.id.email_user)
        emailUser.text = user?.email ?: ""

        val logout = binding.navView.menu.getItem(4)
        logout.setOnMenuItemClickListener { onContextItemSelected(it) }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_admin)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        return true
    }
}