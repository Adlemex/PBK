package com.adlemgames.pbk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.adlemgames.pbk.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        val mySharedPreferences = getSharedPreferences("intro", Context.MODE_PRIVATE)
        if(!mySharedPreferences.contains("showed")) {
            startActivity(Intent(baseContext, Intro::class.java))
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.TeoryFragment,
            R.id.CalcFragment,
            R.id.TablesFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.fr_calc
        bottomNav.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
            when (it.itemId) {
                R.id.fr_teory -> {
                    findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.to_teory)
                    return@OnItemSelectedListener true
                }
                R.id.fr_calc -> {
                    findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.to_calc)
                    return@OnItemSelectedListener true
                }
                R.id.fr_tables -> {
                    findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.to_tables)
                    return@OnItemSelectedListener true
                }
                R.id.fr_blocks -> {
                    //findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.to_other)
                    return@OnItemSelectedListener true
                }
                else -> {return@OnItemSelectedListener false}
            } })
        bottomNav.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.fr_teory -> {
                }
                R.id.fr_calc -> {
                    //findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.to_contacts)
                }
                R.id.fr_tables -> {
                }
                else -> {}
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}