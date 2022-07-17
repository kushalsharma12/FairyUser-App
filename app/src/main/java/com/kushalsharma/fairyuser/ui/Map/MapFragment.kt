package com.kushalsharma.fairyuserapp.ui.Map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kushalsharma.fairyuserapp.databinding.FragmentMapBinding

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}