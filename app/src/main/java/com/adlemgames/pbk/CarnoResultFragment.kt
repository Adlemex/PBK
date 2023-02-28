package com.adlemgames.pbk

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.navigateUp
import com.adlemgames.pbk.databinding.FragmentResultTablesBinding
import com.adlemgames.pbk.models.TruthTables
import com.beust.klaxon.Klaxon
import okhttp3.*
import xdroid.toaster.Toaster.toast
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CarnoResultFragment(exp: String) : Fragment() { // фрагмент для показа карт карно
    val exp = exp
    private var _binding: FragmentResultTablesBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    //val args: TablesResultFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentResultTablesBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val req = Request.Builder() // запрос на сервер
            .url("https://pbk-psu.ml/api/karnaugh_map?funcs=$exp")
            .get()
            .cacheControl(CacheControl.Builder().maxStale(365, TimeUnit.DAYS).build())
            .build()
        Client.okhttp_client.newCall(req).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val serverAnswer = response.body!!.string()
                val mainHandler = Handler(Looper.getMainLooper());
                val myRunnable = Runnable {
                    binding.progressBar.visibility = View.INVISIBLE
                    if (response.code != 200){ // если ошибка
                        when (response.code) {
                            403 -> binding.textView3.text = "К сожалению символ ¬, пока не поддерживается"
                            421 -> {
                                binding.textView3.text = "Ошибка в выражении\n или в нем < 6 входов!"
                            }
                            else -> toast("Ошибка на сервере")
                        }
                        return@Runnable
                    }
                    show(serverAnswer) // показать карты карно
                }
                mainHandler.post(myRunnable);
            }

        })

    }
    fun show(res: String){
        try {
            val result = Klaxon()
                .parse<TruthTables>(res)
            println(result)
            if (result != null) {
                val BOOKSHELF_ROWS = result.data.size// как-то делает таблицу
                val BOOKSHELF_COLUMNS = result.data[0].size
                val tableLayout = binding.table
                tableLayout.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
                tableLayout.dividerDrawable = resources.getDrawable(R.drawable.rectangle_2)
                tableLayout.dividerPadding = 1
                for (i in 0 until BOOKSHELF_ROWS) {
                    val tableRow = TableRow(requireContext())
                    tableRow.dividerPadding = 0
                    tableRow.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
                    tableRow.dividerDrawable = resources.getDrawable(R.drawable.rectangle_1)
                    for (j in 0 until BOOKSHELF_COLUMNS) {
                        val view = View.inflate(requireContext(), R.layout.table_item, null)
                        val textView = view.findViewById<TextView>(R.id.item_text)
                        val text = result.data.get(i).get(j)
                        //if (text.length > 6) textView.setBackgroundResource(R.drawable.rectangle_2)
                        textView.text = text
                        tableRow.addView(textView, j)
                    }
                    tableLayout.addView(tableRow, i)
                }
            }
            else {
                Toast.makeText(requireContext(), "Произошла ошибка", Toast.LENGTH_LONG).show()
                //binding.textView.text = "Ошибка"
                findNavController().navigateUp()
            }
        }
        catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(requireContext(), "Неверный ввод", Toast.LENGTH_LONG).show()
            findNavController().navigateUp()
            Log.d("error", res)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}