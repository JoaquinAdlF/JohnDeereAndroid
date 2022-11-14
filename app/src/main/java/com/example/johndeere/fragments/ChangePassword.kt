package com.example.johndeere.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.johndeere.R
import com.example.johndeere.databinding.FragmentChangePasswordBinding

/*
Fragmento para el cambio de contraseña. Aún no concluido.
 */

class ChangePassword : Fragment() {
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val changePass = view.findViewById<Button>(R.id.changePass_button)
        val back = view.findViewById<Button>(R.id.back_button)

        changePass.setOnClickListener{
            view.findNavController().navigate(R.id.action_changePassword_frag_to_homePage_frag)
        }

        back.setOnClickListener{
            view.findNavController().navigate(R.id.action_changePassword_frag_to_profile_frag)
        }
    }
}