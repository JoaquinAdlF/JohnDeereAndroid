package com.example.johndeere.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.johndeere.R
import com.example.johndeere.databinding.FragmentProfileBinding

/*
Fragmento para la consulta de datos personales del usuario
 */

class Profile : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val changePass = view.findViewById<Button>(R.id.changePass_button)
        val logOut = view.findViewById<Button>(R.id.logOut_button)

        // Recuperación de los datos del usuario previemente obtenidos en el inicio de sesión
        val sharedPref = this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)

        val firstname = sharedPref.getString("firstname", "")
        val lastname = sharedPref.getString("lastname", "")
        val level1 = sharedPref.getFloat("level1", 0f)
        val level2 = sharedPref.getFloat("level2", 0f)
        val level3 = sharedPref.getFloat("level3", 0f)

        // Actualización de la información en la interfaz del usuario
        if (firstname == "") {
            binding.fullnameTxt.text = "Invitado"
        } else {
            binding.fullnameTxt.text = firstname + " " + lastname
        }

        binding.progressBar1.progress = level1.toInt()
        binding.progressBar2.progress = level2.toInt()
        binding.progressBar2.progress = level3.toInt()


        changePass.setOnClickListener{
            view.findNavController().navigate(R.id.action_profile_frag_to_changePassword_frag)
        }

        // Eliminación de los datos de usuario para el cierre de su sesión
        logOut.setOnClickListener{

            with(sharedPref.edit()) {
                putString("firstname", "")
                putString("lastname", "")
                //putFloat("orgid", employee.organizationid!!.toFloat())
                putFloat("level1", 20f)
                putFloat("level2", 55f)
                putFloat("level3", 87f)
                apply()
            }

            view.findNavController().navigate(R.id.action_profile_frag_to_homePage_frag)
        }
    }
}