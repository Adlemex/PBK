package com.adlemgames.pbk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.adlemgames.pbk.databinding.FragmentResultTablesBinding
import com.adlemgames.pbk.databinding.FragmentResultTabsBinding
import com.adlemgames.pbk.models.TruthTables
import com.beust.klaxon.Klaxon
import com.google.android.material.tabs.TabLayoutMediator
import okhttp3.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class TabsResultFragment : Fragment() { // сюда переходим с ввода логического выражения и тут вкладки с разными операциями

    private var _binding: FragmentResultTabsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    val tabs = mutableListOf("Таблицы истинности", "Карты Карно", "Преобразования") // названия вкладок
    private val binding get() = _binding!!
    val args: TabsResultFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentResultTabsBinding.inflate(inflater, container, false)
        binding.pager.adapter = DemoCollectionAdapter(this, exp=args.exp)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabs[position]
        }.attach() // аттачим таб медиатор
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    internal class DemoCollectionAdapter(fragment: Fragment, exp: String) : FragmentStateAdapter(fragment) {
        val exp = exp
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            // Return a NEW fragment instance in createFragment(int)

            val fragment = when (position){ // определяем куда перейти
                0 -> TablesResultFragment(exp)
                1 -> CarnoResultFragment(exp)
                else -> ChangesResultFragment(exp)
            }
            return fragment
        }
    }
}