package com.example.johndeere.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.johndeere.R
import com.example.johndeere.databinding.FragmentMainPageBinding

/*
Sección principal de la Aplicación, donde el usuario elige entre
lenguaje general o lenguaje técnico.
 */

class MainPage : Fragment() {
    private var _binding: FragmentMainPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Botón de lenguaje general
        binding.generalLanguageButton.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("language", "Lenguaje General")
            view.findNavController().navigate(R.id.action_mainPage_frag_to_categories_frag, bundle)
        }

        // Botón de lenguaje técnico
        binding.technicalLanguageButton.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("language", "Lenguaje Técnico")
            view.findNavController().navigate(R.id.action_mainPage_frag_to_categories_frag,bundle)
        }
    }
}