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
import com.adlemgames.pbk.models.Teory.Types
import com.adlemgames.pbk.models.TeoryTest
import com.adlemgames.pbk.models.TeoryTests


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
        Teory("Конечные автоматы", R.drawable.business_target, Types.FOLDER, "Конечные автоматы",
        mutableListOf(
            Teory("D-триггер", R.drawable.target, Types.LIST, "D-триггер.html", null),
            Teory("Анализ конечных автоматов", R.drawable.analysis, Types.LIST, "Анализ конечных автоматов.html", null),
            Teory("Минимизация автоматов", R.drawable.loss, Types.LIST, "Минимизация автоматов.html", null),
            Teory("Основные положения", R.drawable.bank, Types.LIST, "Основные положения.html", null),
            Teory("Представление конечных автоматов", R.drawable.globe, Types.LIST, "Представление конечных автоматов.html", null),
            Teory("Система конечных автоматов", R.drawable.strategy, Types.LIST, "Система конечных автоматов.html", null),
            Teory("Т – триггер", R.drawable.target, Types.LIST, "Т – триггер.html", null),
            Teory("Типы конечных автоматов", R.drawable.price_tag, Types.LIST, "Типы конечных автоматов.html", null),
            Teory("Триггер как конечный автомат", R.drawable.check_mark, Types.LIST, "Триггер как конечный автомат.html", null),
            Teory("Триггеры", R.drawable.keynote, Types.LIST, "Триггеры.html", null),
            )
        ),
        Teory("Машинные методы деления", R.drawable.suitcase, Types.FOLDER, "Машинные методы деления",
        mutableListOf(
            Teory("Блок схема организации вычислений имеет вид", R.drawable.contract, Types.LIST, "Блок схема организации вычислений имеет вид.html", null),
            Teory("Деление в дополнительных кодах", R.drawable.presentation, Types.LIST, "Деление в дополнительных кодах.html", null),
            Teory("Машинные методы деления", R.drawable.bar_chart, Types.LIST, "Машинные методы деления.html", null),
            Teory("Ускоренное выполнени умножения", R.drawable.bar_chart_up, Types.LIST, "Методы ускоренного выполнения операции умножения.html", null),
            Teory("Одновременное умножение на 2 разряда", R.drawable.binoculars, Types.LIST, "Одновременное умножение на 2 разряда.html", null),
            Teory("Схема ускоренного умножения", R.drawable.business_chart, Types.LIST, "Схема ускоренного умножения.html", null),
            )
        ),
        Teory("Регистры", R.drawable.calculator, Types.LIST, "Регистры.html", null),
        Teory("Логические основы вычислительной техники", R.drawable.goal, Types.FOLDER, "Логические основы вычислительной техники",
        mutableListOf(
            Teory("Двоичные переменные и булевы функции", R.drawable.briefcase, Types.LIST, "Двоичные переменные и булевы функции.html", null),
            Teory("Метод карт Карно", R.drawable.check_mark, Types.LIST, "Метод карт Карно.html", null),
            Teory("Метод Квайна – Мак", R.drawable.check_mark, Types.LIST, "Метод Квайна – Мак.html", null),
            Teory("Минимизация логических выражений", R.drawable.revenue_down, Types.LIST, "Минимизация логических выражений.html", null),
            Teory("Нормальные формы представления булевых функций", R.drawable.search_file, Types.LIST, "Нормальные формы представления булевых функций.html", null),
            Teory("Основные законы булевой алгебра", R.drawable.communication, Types.LIST, "Основные законы булевой алгебра.html", null),
            Teory("Представление логических выражений в различных баз", R.drawable.start_up, Types.LIST, "Представление логических выражений в различных баз.html", null),
            Teory("Формы представления булевых функций", R.drawable.hierarchy, Types.LIST, "Формы представления булевых функций.html", null),
        )),
        Teory("Тесты", R.drawable.bar_chart, Types.FOLDER, "Тесты", mutableListOf(
            TeoryTests("Сложение в 2-ой", R.drawable.calculator, mutableListOf(
            TeoryTest("Сложение в 2-ой", R.drawable.calculator, Types.TEST, "1001.1011001 + 1101.1001100\n" +
                                                            "Ответ в 10-ой системе", mutableListOf(
                "-8.7",
                "88.7",
                "7.8",
                "87.7"
            ), mutableListOf(0)),
            TeoryTest("Сложение в 2-ой 2", R.drawable.calculator, Types.TEST, "0110.0100110 + 1101.1001100\n" +
                                    "Ответ в 10-ой системе", mutableListOf(
                "90.3",
                "-9.3",
                "3.9",
                "9.3"
            ), mutableListOf(2)),
            TeoryTest("Сложение в 2-ой 3", R.drawable.calculator, Types.TEST, "1001.1011001 + 0010.0110011\n" +
                                    "Ответ в 10-ой системе", mutableListOf(
                "90.3",
                "30.9",
                "9.3",
                "-3.9",

            ), mutableListOf(3)),
            TeoryTest("Сложение в 2-ой 4", R.drawable.calculator, Types.TEST, "0110.0100110 + 0010.0110011\n" +
                                    "Ответ в 10-ой системе", mutableListOf(
                "9.3",
                "8.7",
                "-7.8",
                "-9.3",

            ), mutableListOf(1)),
            )),
            TeoryTests("Перевод из 2-ой в 10-ую", R.drawable.business_idea, mutableListOf(
                TeoryTest("Перевод 1", R.drawable.calculator, Types.TEST, "0110.001101\n" +
                        "Переведи в 10-ую систему", mutableListOf(
                    "2.6",
                    "8.7",
                    "6.2",
                    "62.2"
                ), mutableListOf(2)),
                TeoryTest("Перевод 2", R.drawable.calculator, Types.TEST, "1100.1010\n" +
                        "Переведи в 10-ую систему", mutableListOf(
                    "126.25",
                    "-126.625",
                    "12.625",
                    "12.25"
                ), mutableListOf(2)),
                TeoryTest("Перевод 3", R.drawable.calculator, Types.TEST, "0011.1101\n" +
                        "Переведи в 10-ую систему", mutableListOf(
                    "80.3",
                    "30.8",
                    "8.3",
                    "3.8",

                    ), mutableListOf(3)),
                TeoryTest("Перевод 4", R.drawable.calculator, Types.TEST, "1010.0000111\n" +
                        "Переведи в 10-ую систему", mutableListOf(
                    "11.1",
                    "10.1",
                    "11",
                    "10.11",

                    ), mutableListOf(1)),
            )),
            TeoryTests("Перевод из 10-ой в 2-ую", R.drawable.business_idea, mutableListOf(
                TeoryTest("Перевод 1", R.drawable.calculator, Types.TEST, "10,5\n" +
                        "Переведи в 2-ую систему", mutableListOf(
                    "1010.1",
                    "0101.01",
                    "1011.1",
                    "0111.1"
                ), mutableListOf(0)),
                TeoryTest("Перевод 2", R.drawable.calculator, Types.TEST, "8\n" +
                        "Переведи в 2-ую систему", mutableListOf(
                    "100",
                    "1000",
                    "1100",
                    "1110",

                    ), mutableListOf(1)),
                TeoryTest("Перевод 3", R.drawable.calculator, Types.TEST, "-6,3\n" +
                        "Переведи в 2-ую систему", mutableListOf(
                    "1010.1",
                    "10011.11",
                    "1001.1011",
                    "1010.11"
                ), mutableListOf(2)),
                TeoryTest("Перевод 4", R.drawable.calculator, Types.TEST, "16,25\n" +
                        "Переведи в 2-ую систему", mutableListOf(
                    "10000.01",
                    "10100.1",
                    "1010.11",
                    "1001.10",

                    ), mutableListOf(0)),
            ))
        ))
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

    }

    override fun folder(teory: Teory) {

    }

    override fun click(teory: Teory?) {
        when (teory?.type){
            Types.FOLDER -> {
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
            Types.LIST -> {
                val action = TeoryViewFragmentDirections.toViewTeory(directory + teory.path, teory.name)
                findNavController().navigate(action)
            }
            Types.TEST -> {
                val test = teory as TeoryTests
                val action = TestFragmentDirections.toTest(test.questions.toTypedArray())
                findNavController().navigate(action)
            }
            Types.MULTI_TEST -> {
                val test = teory as TeoryTests
                val action = TestFragmentDirections.toTest(test.questions.toTypedArray())
                findNavController().navigate(action)

            }
            null -> {}
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