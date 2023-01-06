package com.adlemgames.pbk

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.adlemgames.pbk.databinding.FragmentCalculateActBinding
import com.adlemgames.pbk.databinding.FragmentCalculateBinding
import com.adlemgames.pbk.models.Calc
import com.adlemgames.pbk.models.TruthTables
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import okhttp3.*
import java.io.IOException


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CalcActionsFragment : Fragment() {

    private var _binding: FragmentCalculateActBinding? = null
    var solving: List<Calc.StepBlock> = listOf()
    // This property is only valid between onCreateView and
    // onDestroyView.
    companion object {
        var inpText: String = ""
    }
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCalculateActBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actions = mutableListOf(binding.plus, binding.minus, binding.divide, binding.multiply)
        //for (act in actions){
        //    (act as View).the
        //}
        binding.solving.setOnClickListener {
            if (solving.isEmpty()) return@setOnClickListener
            val action = CalcStepsFragmentDirections.toCalcSteps(solving.toTypedArray())
            findNavController().navigate(action)
        }
        binding.buttonRes.setOnClickListener {
            binding.itogText.text = ""
            binding.progressLoader.visibility = View.VISIBLE
            val request: Request = Request.Builder()
                .url("https://pbk-psu.ml/api/calc?num1=${binding.inputOneText.text}&num2=${binding.inputTwoText.text}&base1=" +
                        "${binding.spinnerFrom.selectedItemPosition+2}&base2=${binding.spinnerTo.selectedItemPosition+2}&action=${"sum"}" +
                        "&end_base=${binding.spinnerItog.selectedItemPosition+2}")
                .get()
                .build()
            Client.okhttp_client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                    val mainHandler = Handler(Looper.getMainLooper());
                    val myRunnable = Runnable {
                        Toast.makeText(requireContext(), "Произошла какая-то ошибка!", Toast.LENGTH_LONG).show()
                        binding.progressLoader.visibility = View.GONE
                    }

                    mainHandler.post(myRunnable);
                }

                override fun onResponse (call: Call, response: Response) {
                    val serverAnswer = response.body!!.string()
                    val mainHandler = Handler(Looper.getMainLooper());
                    if (response.code == 403){
                        val parser: Parser = Parser.default()
                        val stringBuilder: StringBuilder = StringBuilder(serverAnswer)
                        val json: JsonObject = parser.parse(stringBuilder) as JsonObject
                        val mainHandler = Handler(Looper.getMainLooper());
                        val myRunnable = Runnable{
                            try {
                                Toast.makeText(requireContext(), json.string("detail"), Toast.LENGTH_LONG).show()
                            } catch (e: Exception) {
                            } finally {
                                binding.progressLoader.visibility = View.GONE
                                return@Runnable
                            }
                        }
                        mainHandler.post(myRunnable);
                    }
                    val myRunnable = Runnable {
                        if (binding != null) {
                            try {
                                val res = Klaxon()
                                    .parse<Calc>(serverAnswer)
                                if (res!!.result != null) {
                                        binding.itogText.text = res.result.toString()
                                    println(res.toString())
                                        solving = res.blocks!!
                                } else Toast.makeText(
                                        requireContext(),
                                        serverAnswer,
                                        Toast.LENGTH_LONG).show()
                            }
                            finally {

                                binding.progressLoader.visibility = View.GONE
                            }
                        }
                    }
                    if (response.isSuccessful) mainHandler.post(myRunnable);
                }
            })
        }
        var list: List<Int> = ArrayList()
        for(i in 2..16) list += i
        val staticAdapter = ArrayAdapter(requireContext(), R.layout.spiner_item, list)
        staticAdapter.setDropDownViewResource(R.layout.dropdown_item)
        binding.spinnerFrom.adapter = staticAdapter
        binding.spinnerTo.adapter = staticAdapter
        binding.spinnerItog.adapter = staticAdapter
        binding.spinnerFrom.setSelection(8, true)
        binding.spinnerTo.setSelection(8, true)
        binding.spinnerItog.setSelection(8, true)
        binding.spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}