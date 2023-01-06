package com.adlemgames.pbk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.adlemgames.pbk.databinding.FragmentTablesBinding
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class TablesFragment : Fragment() {

    private var _binding: FragmentTablesBinding? = null
    var keyboard: List<Button> = ArrayList<Button>()
    var text: String = ""
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
        val prefs: SharedPreferences = requireContext().getSharedPreferences("tables", Context.MODE_PRIVATE)

        if(prefs.contains("exp")) {
            binding.inputText.text = prefs.getString("exp", "B")
            text = prefs.getString("exp", "").toString()
        }
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
            val textt = binding.inputText.text.toString()
            if (textt.length > 40) {
                Toast.makeText(requireContext(), "Слишком длинная формула", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            text = textt + buttonText
            binding.inputText.text = text
        }
        binding.keyboard.buttonAC.setOnClickListener {
            binding.inputText.text = ""
            text = ""
        }
        binding.keyboard.buttonItog.setOnClickListener {
            //binding.progressLoader.visibility = View.VISIBLE
            /*val request: Request = Request.Builder()
                .url("https://pbk-psu.ml/api/truth?funcs=" + binding.inputText.text)
                .get()
                .cacheControl(CacheControl.Builder().maxStale(365, TimeUnit.DAYS).build())
                .build()
            Client.okhttp_client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    val serverAnswer = response.body!!.string()
                    val mainHandler = Handler(Looper.getMainLooper());

                    val myRunnable = Runnable {*/
                        val action = TabsResultFragmentDirections.toResultTabs(binding.inputText.text.toString())
                        findNavController().navigate(action)
                    /*}
                    mainHandler.post(myRunnable);
                }
            })*/
        }
        binding.keyboard.buttonClean.setOnClickListener {
            val textt = binding.inputText.text
            if (textt.length == 0) return@setOnClickListener
            binding.inputText.text = textt.subSequence(0, textt.length-1)
            text = textt.subSequence(0, textt.length-1) as String
        }
    }

    override fun onPause() {
        val prefs: SharedPreferences = requireContext().getSharedPreferences("tables", Context.MODE_PRIVATE)
        val edit = prefs.edit()
        edit.putString("exp", text)
        edit.apply()
        super.onPause()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}