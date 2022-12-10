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
        Teory("Конечные автоматы", R.drawable.business_target, "Конечные автоматы",
        mutableListOf(
            Teory("D-триггер", R.drawable.target, "D-триггер.html", null),
            Teory("Анализ конечных автоматов", R.drawable.analysis, "Анализ конечных автоматов.html", null),
            Teory("Минимизация автоматов", R.drawable.loss, "Минимизация автоматов.html", null),
            Teory("Основные положения", R.drawable.bank, "Основные положения.html", null),
            Teory("Представление конечных автоматов", R.drawable.globe, "Представление конечных автоматов.html", null),
            Teory("Система конечных автоматов", R.drawable.strategy, "Система конечных автоматов.html", null),
            Teory("Т – триггер", R.drawable.target, "Т – триггер.html", null),
            Teory("Типы конечных автоматов", R.drawable.price_tag, "Типы конечных автоматов.html", null),
            Teory("Триггер как конечный автомат", R.drawable.check_mark, "Триггер как конечный автомат.html", null),
            Teory("Триггеры", R.drawable.keynote, "Триггеры.html", null),
            )
        ),
        Teory("Машинные методы деления", R.drawable.suitcase, "Машинные методы деления",
        mutableListOf(
            Teory("Блок схема организации вычислений имеет вид", R.drawable.contract, "Блок схема организации вычислений имеет вид.html", null),
            Teory("Деление в дополнительных кодах", R.drawable.presentation, "Деление в дополнительных кодах.html", null),
            Teory("Машинные методы деления", R.drawable.bar_chart, "Машинные методы деления.html", null),
            Teory("Ускоренное выполнени умножения", R.drawable.bar_chart_up, "Методы ускоренного выполнения операции умножения.html", null),
            Teory("Одновременное умножение на 2 разряда", R.drawable.binoculars, "Одновременное умножение на 2 разряда.html", null),
            Teory("Схема ускоренного умножения", R.drawable.business_chart, "Схема ускоренного умножения.html", null),
            )
        ),
        Teory("Регистры", R.drawable.calculator, "Регистры.html", null),
        Teory("Логические основы вычислительной техники", R.drawable.goal, "Логические основы вычислительной техники",
        mutableListOf(
            Teory("Двоичные переменные и булевы функции", R.drawable.briefcase, "Двоичные переменные и булевы функции.html", null),
            Teory("Метод карт Карно", R.drawable.check_mark, "Метод карт Карно.html", null),
            Teory("Метод Квайна – Мак", R.drawable.check_mark, "Метод Квайна – Мак.html", null),
            Teory("Минимизация логических выражений", R.drawable.revenue_down, "Минимизация логических выражений.html", null),
            Teory("Нормальные формы представления булевых функций", R.drawable.search_file, "Нормальные формы представления булевых функций.html", null),
            Teory("Основные законы булевой алгебра", R.drawable.communication, "Основные законы булевой алгебра.html", null),
            Teory("Представление логических выражений в различных баз", R.drawable.start_up, "Представление логических выражений в различных баз.html", null),
            Teory("Формы представления булевых функций", R.drawable.hierarchy, "Формы представления булевых функций.html", null),
        )),
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
        setHasOptionsMenu(true)
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
            findNavController().navigate(R.id.to_teory)
            return false
        }
        return false
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