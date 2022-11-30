package com.adlemgames.pbk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.adlemgames.pbk.databinding.FragmentResultTablesBinding
import com.adlemgames.pbk.models.TruthTables
import com.beust.klaxon.Klaxon
import okhttp3.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class TablesResultFragment : Fragment() {

    private var _binding: FragmentResultTablesBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val args: TablesResultFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentResultTablesBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val result = Klaxon()
                .parse<TruthTables>(args.result)
            if (result != null) {
                val BOOKSHELF_ROWS = result.data.size
                val BOOKSHELF_COLUMNS = result.data[0].size

                val tableLayout = binding.table

                for (i in 0 until BOOKSHELF_ROWS) {
                    val tableRow = TableRow(requireContext())
                    tableRow.dividerPadding = 5
                    tableRow.showDividers = LinearLayout.SHOW_DIVIDER_NONE
                    for (j in 0 until BOOKSHELF_COLUMNS) {
                        val view = View.inflate(requireContext(), R.layout.table_item, null)
                        val textView = view.findViewById<TextView>(R.id.item_text)
                        textView.text = result.data.get(i).get(j)
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
            Log.d("error", args.result)
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}