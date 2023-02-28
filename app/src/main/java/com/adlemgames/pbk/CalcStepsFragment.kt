package com.adlemgames.pbk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.adlemgames.pbk.adapters.StepsAdapter
import com.adlemgames.pbk.databinding.FragmentStepsCalcBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CalcStepsFragment : Fragment() { // отобрадает шаги решения

    private var _binding: FragmentStepsCalcBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val args: CalcStepsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStepsCalcBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycleview.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleview.adapter = StepsAdapter(requireContext(), args.blocks) // из массива показать шаги
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}