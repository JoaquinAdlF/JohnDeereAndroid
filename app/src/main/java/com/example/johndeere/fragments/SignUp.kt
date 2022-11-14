package com.example.johndeere.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.johndeere.R
import com.example.johndeere.api.ApiService
import com.example.johndeere.databinding.FragmentSignUpBinding
import com.example.johndeere.models.employeeInfo
import com.example.johndeere.models.organization
import com.example.johndeere.models.progress

/*
Fragmento para el registro de un usuario nuevo.
 */

class SignUp : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpButton.setOnClickListener {
            // Recolección y validación de los valores ingresados por el usuario
            val firstname = binding.signFirstnameTxt.text.toString()
            val lastname = binding.signLastnameTxt.text.toString()
            val email = binding.signEmail.text.toString()
            val password = binding.signPassword.text.toString()
            val companycode = binding.signCompanycode.text.toString()

            if (firstname.isEmpty() || firstname == "") {
                binding.signFirstnameTxt.error = "Nombre requerido"
                binding.signFirstnameTxt.requestFocus()
                return@setOnClickListener
            }
            if (lastname.isEmpty() || lastname == "") {
                binding.signLastnameTxt.error = "Apellido requerido"
                binding.signLastnameTxt.requestFocus()
                return@setOnClickListener
            }
            if (email.isEmpty() || email == "") {
                binding.signEmail.error = "Email requerido"
                binding.signEmail.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty() || password == "") {
                binding.signPassword.error = "Contraseña requerida"
                binding.signEmail.requestFocus()
                return@setOnClickListener
            }
            if (companycode.isEmpty() || companycode == "") {
                binding.signCompanycode.error = "Clave de empresa requerida"
                binding.signCompanycode.requestFocus()
                return@setOnClickListener
            }

            // Construcción del modelo de Organización
            val companyData = organization (
                    id = 0,
                    name = "",
                    companycode = companycode
                    )

            // Solicitud para verificar la existencia de la organización ingresada
            ApiService.verifyOrg(companyData) {
                if (it!!.isEmpty()) {
                    Toast.makeText(context, "Compañía no encontrada", Toast.LENGTH_LONG).show()
                } else {
                    val organizationid = it[0].id

                    // Si la organización está registrada, se contruye el modelo de Empleado con los
                    // datos ingresados y con valores iniciales para el progreso del usuario a registrar
                    val employeeData = employeeInfo (
                        id = 0,
                        firstname = firstname,
                        lastname = lastname,
                        email = email,
                        password = password,
                        companycode = companycode,
                        organizationid = organizationid,
                        level1 = 0,
                        level2 = 0,
                        level3 = 0
                    )

                    // Verificación de la existencia de un usuario con el correo ingresado
                    ApiService.authEmployee(employeeData) {
                        if (it!!.isEmpty()) {
                            ApiService.addEmployee(employeeData) {
                                if (it?.insertId != null) {
                                    val employeeid = it?.insertId

                                    // Si no existe un usuario, se construye el modelo de Progreso
                                    // para el nuevo usuario
                                    val employeeProgress = progress (
                                        id = 0,
                                        level1 = 0,
                                        level2 = 0,
                                        level3 = 0,
                                        employeeid = employeeid
                                            )

                                    // Solicitud de creación de progreso para el nuevo usuario
                                    ApiService.createProgress(employeeProgress) {
                                        // Finalización del registro
                                        if (it?.insertId != null) {
                                            Toast.makeText(context, "Usuario registrado", Toast.LENGTH_LONG).show()
                                        } else {
                                            Toast.makeText(context, "Error al registrarse", Toast.LENGTH_LONG).show()
                                        }
                                    }

                                } else {
                                    Toast.makeText(context, "Error al registrarse", Toast.LENGTH_LONG).show()
                                }
                            }

                        } else {
                            Toast.makeText(context, "Usuario no disponible", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

        }

        binding.backButton.setOnClickListener{
            view.findNavController().navigate(R.id.action_signUp_frag_to_homePage_frag)
        }
    }
}