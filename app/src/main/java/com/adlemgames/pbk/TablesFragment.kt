package com.adlemgames.pbk

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.adlemgames.pbk.databinding.FragmentTablesBinding
import okhttp3.*
import java.io.IOException


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class TablesFragment : Fragment() {

    private var _binding: FragmentTablesBinding? = null
    var keyboard: List<Button> = ArrayList<Button>()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTablesBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // region adding buttons
        keyboard += binding.keyboard.buttonA
        keyboard += binding.keyboard.buttonB
        keyboard += binding.keyboard.buttonC
        keyboard += binding.keyboard.buttonD
        keyboard += binding.keyboard.buttonE
        keyboard += binding.keyboard.buttonF
        keyboard += binding.keyboard.buttonG
        keyboard += binding.keyboard.buttonH
        keyboard += binding.keyboard.buttonF
        keyboard += binding.keyboard.buttonAnd
        keyboard += binding.keyboard.buttonEqual
        keyboard += binding.keyboard.buttonImplies
        keyboard += binding.keyboard.buttonNot
        keyboard += binding.keyboard.buttonOr
        keyboard += binding.keyboard.buttonXor
        keyboard += binding.keyboard.buttonLeft
        keyboard += binding.keyboard.buttonRight
        // endregion
        for (key in keyboard) key.setOnClickListener {
            val buttonText = (it as Button).text.toString()
            val text = binding.inputText.text.toString()
            if (text.length > 40) {
                Toast.makeText(requireContext(), "Слишком длинная формула", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            binding.inputText.text = text + buttonText
        }
        binding.keyboard.buttonAC.setOnClickListener {
            binding.inputText.text = ""
        }
        binding.keyboard.buttonItog.setOnClickListener {
            binding.progressLoader.visibility = View.VISIBLE
            val client = OkHttpClient()
            val request: Request = Request.Builder()
                .url("https://pbk-psu.ml/api/truth?funcs=" + binding.inputText.text)
                .get()
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    val serverAnswer = response.body!!.string()
                    val mainHandler = Handler(Looper.getMainLooper());

                    val myRunnable = Runnable {
                        val action = TablesResultFragmentDirections.toResultTables(serverAnswer)
                        findNavController().navigate(action)
                    }
                    mainHandler.post(myRunnable);
                }
            })
        }
        binding.keyboard.buttonClean.setOnClickListener {
            val text = binding.inputText.text
            binding.inputText.text = text.subSequence(0, text.length-1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}