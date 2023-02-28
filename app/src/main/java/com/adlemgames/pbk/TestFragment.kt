package com.adlemgames.pbk

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewClientCompat
import androidx.webkit.WebViewFeature
import com.adlemgames.pbk.databinding.FragmentTeoryBinding
import com.adlemgames.pbk.databinding.FragmentTeoryViewBinding
import com.adlemgames.pbk.databinding.FragmentTestBinding
import xdroid.toaster.Toaster.toast
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TestFragment : Fragment() { // фрагмент теста

    private var _binding: FragmentTestBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val args: TestFragmentArgs by navArgs() // переданные данные
    var right: Int = 0 // кол-во правильных
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTestBinding.inflate(inflater, container, false)

        val toolbar = (activity as AppCompatActivity?)!!.supportActionBar!!
        //toolbar.title = args.name
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        draw(0)
    }

    fun draw(pos: Int){ // рисуем вопросы
        binding.textView.text = args.data[pos].question
        binding.radioButton.text = args.data[pos].answers[0]
        binding.radioButton2.text = args.data[pos].answers[1]
        binding.radioButton3.text = args.data[pos].answers[2]
        binding.radioButton4.text = args.data[pos].answers[3]
        binding.button.setOnClickListener {
            val selected = when(binding.radioGroup.checkedRadioButtonId){
                R.id.radioButton -> 0
                R.id.radioButton2 -> 1
                R.id.radioButton3 -> 2
                R.id.radioButton4 -> 3
                else -> 100
            }
            if (selected == 100){
                toast("Выберите вариант ответа")
                return@setOnClickListener
            }
            if (args.data[pos].right_ans[0] == selected){
                toast("Правильно")
                right += 1
            }
            else toast("Неправильно")
            println(pos)
            binding.radioGroup.clearCheck()
            println(args.data.size)
            if ((pos + 1) == args.data.size){
                val action = TestResultFragmentDirections.toTestResult(right, args.data.size)
                findNavController().navigate(action)
            }
            else draw(pos + 1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}