package com.adlemgames.pbk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.adlemgames.pbk.adapters.RecycleAdapterTeory
import com.adlemgames.pbk.databinding.FragmentTeoryBinding
import com.adlemgames.pbk.interfaces.BackButtonHandlerInterface
import com.adlemgames.pbk.interfaces.OnBackClickListener
import com.adlemgames.pbk.models.Teory


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TeoryFragment : Fragment(), RecycleAdapterTeory.Callback, OnBackClickListener {

    private var _binding: FragmentTeoryBinding? = null
    var state: Int = 0
    var directory: String = ""
    // This property is only valid between onCreateView and
    // onDestroyView.
    var teoryes: MutableList<Teory> = mutableListOf(
        Teory("Конечные автоматы", R.drawable.ic_baseline_calc_500, "Конечные автоматы",
        mutableListOf(
            Teory("D-триггер", R.drawable.ic_baseline_upcoming_24, "D-триггер.html", null),
            Teory("Анализ конечных автоматов", R.drawable.ic_baseline_analytics_24, "Анализ конечных автоматов.html", null),
            Teory("Минимизация автоматов", R.drawable.ic_baseline_upcoming_24, "Минимизация автоматов.html", null),
            Teory("Основные положения", R.drawable.ic_baseline_home_24, "Основные положения.html", null),
            Teory("Представление конечных автоматов", R.drawable.ic_baseline_preview_24, "Представление конечных автоматов.html", null),
            Teory("Система конечных автоматов", R.drawable.ic_baseline_preview_24, "Система конечных автоматов.html", null),
            Teory("Т – триггер", R.drawable.ic_baseline_preview_24, "Т – триггер.html", null),
            Teory("Типы конечных автоматов", R.drawable.ic_baseline_preview_24, "Типы конечных автоматов.html", null),
            Teory("Триггер как конечный автомат", R.drawable.ic_baseline_preview_24, "Триггер как конечный автомат.html", null),
            Teory("Триггеры", R.drawable.ic_baseline_preview_24, "Триггеры.html", null),
            )
        ),
        Teory("Машинные методы деления", R.drawable.ic_baseline_calc_500, "Машинные методы деления",
        mutableListOf(
            Teory("Блок схема организации вычислений имеет вид", R.drawable.ic_baseline_upcoming_24, "Блок схема организации вычислений имеет вид.html", null),
            Teory("Деление в дополнительных кодах", R.drawable.ic_baseline_analytics_24, "Деление в дополнительных кодах.html", null),
            Teory("Машинные методы деления", R.drawable.ic_baseline_upcoming_24, "Машинные методы деления.html", null),
            Teory("Методы ускоренного выполнения операции умножения", R.drawable.ic_baseline_home_24, "Методы ускоренного выполнения операции умножения.html", null),
            Teory("Одновременное умножение на 2 разряда", R.drawable.ic_baseline_preview_24, "Одновременное умножение на 2 разряда.html", null),
            Teory("Схема ускоренного умножения", R.drawable.ic_baseline_preview_24, "Схема ускоренного умножения.html", null),
            )
        ),
    )
    lateinit var adapter: RecycleAdapterTeory
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        if (directory != ""){
            val toolbar = (activity as AppCompatActivity?)!!.supportActionBar!!
            toolbar.title = directory.replace("/", "")
            toolbar.setDisplayHomeAsUpEnabled(true)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = RecycleAdapterTeory(requireActivity(), teoryes, this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeoryBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as BackButtonHandlerInterface).addBackClickListener(this)
        binding.recycle.layoutManager = LinearLayoutManager(requireContext())
        binding.recycle.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as BackButtonHandlerInterface).removeBackClickListener(this)


        _binding = null
    }

    override fun paragraph(teory: Teory) {
        val action = TeoryViewFragmentDirections.toViewTeory(directory + teory.path, teory.name)
        findNavController().navigate(action)
    }

    override fun folder(teory: Teory) {
        directory = teory.path + "/"
        val toolbar = (activity as AppCompatActivity?)!!.supportActionBar!!
        toolbar.title = teory.name
        toolbar.setDisplayHomeAsUpEnabled(true)
        state = 1
        if (teory.child != null){
            adapter = RecycleAdapterTeory(requireActivity(), teory.child, this)
            binding.recycle.adapter = adapter
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            adapter = RecycleAdapterTeory(requireActivity(), teoryes, this)
            binding.recycle.adapter = adapter
            state = 0
            directory = ""
            val toolbar = (activity as AppCompatActivity?)!!.supportActionBar!!
            toolbar.title = "Теория"
            toolbar.setDisplayHomeAsUpEnabled(false)
            return true
        }
        return super.onOptionsItemSelected(menuItem)
    }

    override fun onBackClick(): Boolean {
        if (state == 1) {
            directory = ""
            adapter = RecycleAdapterTeory(requireActivity(), teoryes, this)
            binding.recycle.adapter = adapter
            state = 0
            val toolbar = (activity as AppCompatActivity?)!!.supportActionBar!!
            toolbar.title = "Теория"
            toolbar.setDisplayHomeAsUpEnabled(false)
            return true
        } else return false
    }

}