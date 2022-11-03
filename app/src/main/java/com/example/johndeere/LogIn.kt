package com.example.johndeere

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.johndeere.databinding.FragmentLogInBinding

class LogIn : Fragment() {
    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLogInBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val logIn = view.findViewById<Button>(R.id.logIn_button)
        val back = view.findViewById<Button>(R.id.back_button)

        logIn.setOnClickListener{
            view.findNavController().navigate(R.id.action_logIn_frag_to_mainPage_frag)
        }

        back.setOnClickListener{
            view.findNavController().navigate(R.id.action_logIn_frag_to_homePage_frag)
        }
    }
}