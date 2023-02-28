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
import com.adlemgames.pbk.interfaces.BackButtonHandlerInterface
import com.adlemgames.pbk.interfaces.OnBackClickListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import okhttp3.Cache
import java.lang.ref.WeakReference

// Главная активити
class MainActivity : AppCompatActivity(), BackButtonHandlerInterface {

    private lateinit var bottomNav: BottomNavigationView // нижняя панелька
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Client.create(baseContext) // создаем клиент для запросов к серверц
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        val mySharedPreferences = getSharedPreferences("intro", Context.MODE_PRIVATE) //проверяем, показывали интро или нет
        if(!mySharedPreferences.contains("showed")) {
            startActivity(Intent(baseContext, Intro::class.java))
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        val appBarConfiguration = AppBarConfiguration(setOf( // устанавливаем фрагменты на которых не нужна кнопка назад сверху
            R.id.TeoryFragment,
            R.id.CalcFragment,
            R.id.TablesFragment,
            R.id.BlocksFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.fr_calc // страница при открытии - калькулятор
        bottomNav.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { // ставим листенеры на нажатия кнопок на нижней панели
            when (it.itemId) {
                R.id.fr_teory -> {
                    findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.to_teory) // перейти на вкладку теория
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
                    findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.to_blocks)
                    return@OnItemSelectedListener true
                }
                else -> {return@OnItemSelectedListener false}
            } })
        bottomNav.setOnItemReselectedListener { // просто надо
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
        menuInflater.inflate(R.menu.menu_main, menu) // создаем верхнее меню (где расширенный калькулятор)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {// листенеры для верхнего меню
            R.id.action_calc -> {
                findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.to_calc_acts)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private val backClickListenersList: ArrayList<WeakReference<OnBackClickListener>> = ArrayList() // нужно для работы кнопки назад на вкладке теории

    override fun addBackClickListener(onBackClickListener: OnBackClickListener?) {
        backClickListenersList.add(WeakReference(onBackClickListener))
    }

    override fun removeBackClickListener(onBackClickListener: OnBackClickListener) {
        val iterator: MutableIterator<WeakReference<OnBackClickListener>> =
            backClickListenersList.iterator()
        while (iterator.hasNext()) {
            val weakRef: WeakReference<OnBackClickListener> = iterator.next()
            if (weakRef.get() === onBackClickListener) {
                iterator.remove()
            }
        }
    }

    override fun onBackPressed() {
        if (!fragmentsBackKeyIntercept()) {
            super.onBackPressed()
        }
    }

    private fun fragmentsBackKeyIntercept(): Boolean {
        var isIntercept = false
        for (weakRef in backClickListenersList) {
            val onBackClickListener: OnBackClickListener? = weakRef.get()
            if (onBackClickListener != null) {
                val isFragmIntercept = onBackClickListener.onBackClick()
                if (!isIntercept) isIntercept = isFragmIntercept
            }
        }
        return isIntercept
    }
}