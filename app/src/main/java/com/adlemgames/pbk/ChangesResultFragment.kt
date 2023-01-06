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
import com.adlemgames.pbk.databinding.FragmentResultLogicBinding
import com.adlemgames.pbk.databinding.FragmentResultTablesBinding
import com.adlemgames.pbk.models.LogicChanges
import com.adlemgames.pbk.models.TruthTables
import com.beust.klaxon.Klaxon
import okhttp3.*
import xdroid.toaster.Toaster.toast
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ChangesResultFragment(exp: String) : Fragment() {
    val exp = exp
    private var _binding: FragmentResultLogicBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    //val args: TablesResultFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentResultLogicBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val req = Request.Builder()
            .url("https://pbk-psu.ml/api/changes?funcs=$exp")
            .get()
            .cacheControl(CacheControl.Builder().maxStale(365, TimeUnit.DAYS).build())
            .build()
        Client.okhttp_client.newCall(req).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val serverAnswer = response.body!!.string()
                val mainHandler = Handler(Looper.getMainLooper());
                val myRunnable = Runnable {
                    binding.progressBar.visibility = View.INVISIBLE
                    if (response.code == 421) binding.textView3.text = "Неверный ввод"
                    else if (response.code == 400) binding.textView3.text = "К сожалению символ ¬, пока не поддерживается"
                    else if (response.code == 412) binding.textView3.text = "Произошла ошибка"
                    else if (response.code == 404) binding.textView3.text = "Сервер не отвечает"
                    else if (response.code >= 500) binding.textView3.text = "Сервер не доступен"
                    show(serverAnswer)
                }
                mainHandler.post(myRunnable);
            }

        })

    }
    fun show(res: String){
        try {
            println(res)
            val result = Klaxon()
                .parse<LogicChanges>(res)
            if (result != null) {
                binding.result.visibility = View.VISIBLE
                binding.sknf.text = result.sknf
                binding.sdnf.text = result.sdnf
                binding.simplify.text = result.simplified
            }
            else {
            }
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}