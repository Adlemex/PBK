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
import com.adlemgames.pbk.databinding.FragmentTestResultBinding
import xdroid.toaster.Toaster.toast
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TestResultFragment : Fragment() { // показ результата теста

    private var _binding: FragmentTestResultBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val args: TestResultFragmentArgs by navArgs()
    var right: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTestResultBinding.inflate(inflater, container, false)

        val toolbar = (activity as AppCompatActivity?)!!.supportActionBar!!
        //toolbar.title = args.name
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.result.text = "${args.right}/${args.all}"
        if (args.right == args.all) binding.text.text = "Замечательно!"// мотивирующий текст
        else if (args.all - args.right <= 2) binding.text.text = "Чуть-чуть не хватило!"
        else binding.text.text = "Можно было лучше!"
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}