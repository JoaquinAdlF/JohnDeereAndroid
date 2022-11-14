package com.example.johndeere.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.johndeere.R
import com.example.johndeere.api.ApiService
import com.example.johndeere.databinding.FragmentLogInBinding
import com.example.johndeere.models.employeeInfo

/*
Fragmento de inició de sesión del usuario.
 */

class LogIn : Fragment() {
    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logInButton.setOnClickListener{

            // Captura y validación de datos de entrada
            val email = binding.logEmail.text.toString()
            val password = binding.logPassword.text.toString()

            if (email.isEmpty() || email == "") {
                binding.logEmail.error = "Email requerido"
                binding.logEmail.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty() || password == "") {
                binding.logPassword.error = "Contraseña requerida"
                binding.logPassword.requestFocus()
                return@setOnClickListener
            }

            // Construcción del modelo de Empleado con los datos capturados
            val employeeData = employeeInfo (
                id = 0,
                firstname = "",
                lastname = "",
                email = email,
                password = password,
                companycode = "",
                organizationid = 0,
                level1 = 0,
                level2 = 0,
                level3 = 0
                    )

            // Solicitud de autenticación al API
            ApiService.authEmployee(employeeData) {
                if (it!!.isEmpty()) {
                    Toast.makeText(context, "Usuario o contraseña incorrecta", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        context,
                        "Bienvenido(a) " + it[0].firstname.toString(),
                        Toast.LENGTH_LONG
                    ).show()

                    val employee = it[0]

                    // Almacenamiento local de los datos del usuario que se acaba de autenticar
                    val preferences = this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
                    with(preferences.edit()) {
                        putFloat("id", employee.id!!.toFloat())
                        putString("firstname", employee.firstname!!.toString())
                        putString("lastname", employee.lastname!!.toString())
                        //putFloat("orgid", employee.organizationid!!.toFloat())
                        putFloat("level1", employee.level1!!.toFloat())
                        putFloat("level2", employee.level2!!.toFloat())
                        putFloat("level3", employee.level3!!.toFloat())
                        apply()
                    }

                    view.findNavController().navigate(R.id.action_logIn_frag_to_mainPage_frag)
                }
            }
        }

        binding.backButton.setOnClickListener{
            view.findNavController().navigate(R.id.action_logIn_frag_to_homePage_frag)
        }
    }
}