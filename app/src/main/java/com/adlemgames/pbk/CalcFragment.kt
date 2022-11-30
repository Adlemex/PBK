package com.adlemgames.pbk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.adlemgames.pbk.databinding.FragmentCalculateBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CalcFragment : Fragment() {

    private var _binding: FragmentCalculateBinding? = null
    var keyboard: List<Button> = ArrayList<Button>()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCalculateBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // region adding buttons
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
            val text = binding.inputText.text.toString()
            if (text.length > 20) {
                Toast.makeText(requireContext(), "Слишком большое число", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            binding.inputText.text = text + buttonText
        }
        binding.keyboard.buttonAC.setOnClickListener {
            binding.inputText.text = ""
        }
        binding.keyboard.buttonClean.setOnClickListener {
            val text = binding.inputText.text
            binding.inputText.text = text.subSequence(0, text.length-1)
        }
        binding.keyboard.buttonItog.setOnClickListener {
            binding.progressLoader.visibility = View.VISIBLE
        }
        for(i in 2..16) list += i
        val staticAdapter = ArrayAdapter(requireContext(), R.layout.spiner_item, list)
        staticAdapter .setDropDownViewResource(R.layout.dropdown_item)
        binding.spinnerTo.adapter = staticAdapter
        binding.spinnerTo.setSelection(8, true)
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