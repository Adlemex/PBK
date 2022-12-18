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
class CalcFragment : Fragment() {

    private var _binding: FragmentCalculateBinding? = null
    var keyboard: List<Button> = ArrayList<Button>()
    var solving: List<String> = listOf()
    // This property is only valid between onCreateView and
    // onDestroyView.
    var text: String = ""
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCalculateBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inputText.text = text
        // region adding buttons
        keyboard = listOf()
        keyboard += binding.keyboard.button0
        keyboard += binding.keyboard.button1
        keyboard += binding.keyboard.button2
        keyboard += binding.keyboard.button3
        keyboard += binding.keyboard.button4
        keyboard += binding.keyboard.button5
        keyboard += binding.keyboard.button6
        keyboard += binding.keyboard.button7
        keyboard += binding.keyboard.button8
        keyboard += binding.keyboard.button9
        keyboard += binding.keyboard.buttonA
        keyboard += binding.keyboard.buttonB
        keyboard += binding.keyboard.buttonC
        keyboard += binding.keyboard.buttonD
        keyboard += binding.keyboard.buttonE
        keyboard += binding.keyboard.buttonF
        // endregion
        hideButtons(10)
        var list: List<Int> = ArrayList()
        for (key in keyboard) key.setOnClickListener {
            val buttonText = (it as Button).text.toString()
            if (text.length > 20) {
                Toast.makeText(requireContext(), "Слишком большое число", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            binding.inputText.text = text + buttonText
            binding.itogText.text = ""
            text = binding.inputText.text.toString()
            solving = listOf()
        }
        binding.keyboard.buttonAC.setOnClickListener {
            binding.inputText.text = ""
            text = ""
            binding.itogText.text = ""
            solving = listOf()
        }
        binding.change.setOnClickListener {
            val one = binding.spinnerFrom.selectedItemPosition
            val two = binding.spinnerTo.selectedItemPosition
            binding.spinnerFrom.setSelection(two)
            binding.spinnerTo.setSelection(one)
            binding.itogText.text = ""
            solving = listOf()
        }
        binding.keyboard.buttonClean.setOnClickListener {
            if (text.isEmpty()) return@setOnClickListener
            binding.inputText.text = text.subSequence(0, text.length-1)
            binding.itogText.text = ""
            text = text.subSequence(0, text.length-1) as String
            solving = listOf()
        }
        binding.solving.setOnClickListener {
            if (solving.isEmpty()) return@setOnClickListener
            val action = CalcStepsFragmentDirections.toCalcSteps(solving.toTypedArray())
            findNavController().navigate(action)
        }
        binding.keyboard.buttonItog.setOnClickListener {
            binding.itogText.text = ""
            binding.progressLoader.visibility = View.VISIBLE
            val client = OkHttpClient()
            val request: Request = Request.Builder()
                .url("https://pbk-psu.ml/api/ch_bases?num=" + binding.inputText.text +
                        "&from_base=" + (binding.spinnerFrom.selectedItemPosition+2).toString() +
                        "&to_base=" + (binding.spinnerTo.selectedItemPosition+2).toString())
                .get()
                .build()
            client.newCall(request).enqueue(object : Callback {
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
                                val result = Klaxon()
                                    .parse<Calc>(serverAnswer)
                                if (result!!.result != null) {
                                        binding.itogText.text = result.result.toString()
                                        solving = result.steps!!
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
        for(i in 2..16) list += i
        val staticAdapter = ArrayAdapter(requireContext(), R.layout.spiner_item, list)
        val staticAdapter2 = ArrayAdapter(requireContext(), R.layout.spiner_item, listOf(10))
        staticAdapter.setDropDownViewResource(R.layout.dropdown_item)
        staticAdapter2.setDropDownViewResource(R.layout.dropdown_item)
        binding.spinnerTo.adapter = staticAdapter2
        //binding.spinnerTo.setSelection(8, true)
        binding.spinnerFrom.adapter = staticAdapter
        binding.spinnerFrom.setSelection(8, true)
        binding.spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                hideButtons(p2+2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun hideButtons(base: Int){
        for (key in keyboard) key.isEnabled = false
        for (i in 0 until base) keyboard.get(i).isEnabled = true
    }
}