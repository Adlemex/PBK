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
import androidx.navigation.fragment.navArgs
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewClientCompat
import androidx.webkit.WebViewFeature
import com.adlemgames.pbk.databinding.FragmentTeoryBinding
import com.adlemgames.pbk.databinding.FragmentTeoryViewBinding
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TeoryViewFragment : Fragment() {

    private var _binding: FragmentTeoryViewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val args: TeoryViewFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTeoryViewBinding.inflate(inflater, container, false)

        val toolbar = (activity as AppCompatActivity?)!!.supportActionBar!!
        toolbar.title = args.name
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (WebViewFeature.isFeatureSupported(WebViewFeature.ALGORITHMIC_DARKENING)) {
            WebSettingsCompat.setAlgorithmicDarkeningAllowed(binding.web.settings, true)
        }
        else if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            @Suppress("DEPRECATION")
            WebSettingsCompat.setForceDark(binding.web.settings, WebSettingsCompat.FORCE_DARK_ON)
        }

        binding.web.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                injectCSS()
            }
        }
        binding.web.settings.javaScriptEnabled = true
        binding.web.loadUrl("file:///android_asset/" + args.filePath)
    }
    private fun injectCSS() {
        try {
            val string = when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> "filter: invert(1);"
                else -> ""
            }
            binding.web.loadUrl(
                "javascript:(function() {" +
                        "var css = 'img {display: inline; height: auto; max-width: 100%; " + string + "}'," +
                        "head = document.head || document.getElementsByTagName('head')[0]," +
                        "style = document.createElement('style');" +
                        "style.type = 'text/css';" +
                        "head.appendChild(style);" +
                        "style.appendChild(document.createTextNode(css));" +
                        "})()"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}