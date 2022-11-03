package com.example.johndeere

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.johndeere.databinding.FragmentMainPageBinding

class MainPage : Fragment() {
    private var _binding: FragmentMainPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainPageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lenguajeGeneral = view.findViewById<Button>(R.id.generalLanguage_button)
        val lenguajeTecnico = view.findViewById<Button>(R.id.technicalLanguage_button)

        lenguajeGeneral.setOnClickListener{
            view.findNavController().navigate(R.id.action_mainPage_frag_to_categories_frag)
        }

        lenguajeTecnico.setOnClickListener{
            view.findNavController().navigate(R.id.action_mainPage_frag_to_categories_frag)
        }
    }
}