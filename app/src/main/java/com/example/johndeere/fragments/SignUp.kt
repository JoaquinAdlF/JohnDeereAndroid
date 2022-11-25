package com.example.johndeere.fragments

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.johndeere.R
import com.example.johndeere.api.ApiService
import com.example.johndeere.databinding.FragmentSignUpBinding
import com.example.johndeere.models.employeeInfo
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

    override fun onResume() {
        super.onResume()
        binding.signpbarContainer.visibility = View.GONE
        binding.signUpButton.visibility = View.VISIBLE
        val sharedPref = this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "")
        if (username != "") {
            view?.findNavController()?.navigate(R.id.action_signUp_frag_to_homePage_frag)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usernameFocusListener()
        emailFocusListener()
        passwordFocusListener()
        companynameFocusListener()
        companycodeFocusListener()

        binding.signUpButton.setOnClickListener {
            submitForm()
            binding.signUpButton.visibility = View.GONE
            binding.signpbarContainer.visibility = View.VISIBLE
        }
    }

    private fun submitForm() {
        binding.usernameContainer.helperText = validUsername()
        binding.emailContainer.helperText = validEmail()
        binding.passwordContainer.helperText = validPassword()
        binding.companynameContainer.helperText = validCompanyname()
        binding.companycodeContainer.helperText = validCompanycode()

        val validUsername = binding.usernameContainer.helperText == null
        val validEmail = binding.emailContainer.helperText == null
        val validPassword = binding.passwordContainer.helperText == null
        val validCompanyname = binding.companynameContainer.helperText == null
        val validCompanycode = binding.companycodeContainer.helperText == null

        if (validUsername && validEmail && validPassword && validCompanyname && validCompanycode) {
            if (checkForInternet(this.requireActivity())) {
                sendForm()
            } else {
                AlertDialog.Builder(context)
                    .setTitle("No hay conexión a internet")
                    .setPositiveButton("Ok") { _, _ ->
                        binding.signUpButton.visibility = View.VISIBLE
                        binding.signpbarContainer.visibility = View.GONE
                    }
                    .show()
            }
        }

        else
            invalidForm()
    }

    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    private fun invalidForm()
    {
        var message = ""
        if (binding.usernameContainer.helperText != null)
            message += "\n\nNombre de usuario: " + binding.usernameContainer.helperText
        if (binding.emailContainer.helperText != null)
            message += "\n\nCorreo electrónico: " + binding.emailContainer.helperText
        if (binding.passwordContainer.helperText != null)
            message += "\n\nContraseña: " + binding.passwordContainer.helperText
        if (binding.companynameContainer.helperText != null)
            message += "\n\nNombre de empresa: " + binding.companynameContainer.helperText
        if(binding.companycodeContainer.helperText != null)
            message += "\n\nClave de empresa: " + binding.companycodeContainer.helperText

        AlertDialog.Builder(context)
            .setTitle("Datos no válidos")
            .setMessage(message)
            .setPositiveButton("Ok"){ _,_ ->
                binding.signUpButton.visibility = View.VISIBLE
                binding.signpbarContainer.visibility = View.GONE
            }
            .show()
    }

    private fun sendForm() {
        val employeeData = employeeInfo (
            id = 0,
            username = binding.usernameEditText.text.toString(),
            email = binding.emailEditText.text.toString(),
            password = binding.passwordEditText.text.toString(),
            companyname = binding.companynameEditText.text.toString(),
            companycode = binding.companycodeEditText.text.toString(),
            organizationid = 0,
            level1 = 0,
            level2 = 0,
            level3 = 0,
            message = ""
        )

        ApiService.addEmployee(employeeData) {
            if (it == null) {
                AlertDialog.Builder(context)
                    .setTitle("Error de conectividad")
                    .setPositiveButton("Ok") { _,_ ->
                        binding.signUpButton.visibility = View.VISIBLE
                        binding.signpbarContainer.visibility = View.GONE
                    }
                    .show()
            }
            else {
                if (it?.message == "Company not found") {
                    AlertDialog.Builder(context)
                        .setTitle("Empresa no encontrada")
                        .setPositiveButton("Ok") { _, _ ->
                            binding.signUpButton.visibility = View.VISIBLE
                            binding.signpbarContainer.visibility = View.GONE
                        }
                        .show()
                } else if (it?.message == "User already exists") {
                    AlertDialog.Builder(context)
                        .setTitle("Nombre de usuario no disponible")
                        .setPositiveButton("Ok") { _, _ ->
                            binding.signUpButton.visibility = View.VISIBLE
                            binding.signpbarContainer.visibility = View.GONE
                        }
                        .show()
                } else if (it?.insertId != -1) {
                    val employeeid = it?.insertId
                    val employeeProgress = progress(
                        id = 0,
                        level1 = 0,
                        level2 = 0,
                        level3 = 0,
                        employeeid = employeeid
                    )
                    ApiService.createProgress(employeeProgress) {
                        if (it?.insertId != null) {
                            AlertDialog.Builder(context)
                                .setTitle("Usuario registrado")
                                .setPositiveButton("Ok") { _, _ ->
                                    view?.findNavController()
                                        ?.navigate(R.id.action_signUp_frag_to_homePage_frag)
                                }
                                .show()
                        } else {
                            AlertDialog.Builder(context)
                                .setTitle("Error al registrar usuario")
                                .setPositiveButton("Ok") { _, _ ->
                                    binding.signUpButton.visibility = View.VISIBLE
                                    binding.signpbarContainer.visibility = View.GONE
                                }
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun usernameFocusListener()
    {
        binding.usernameEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.usernameContainer.helperText = validUsername()
            }
        }
    }

    private fun validUsername(): String?
    {
        val usernameText = binding.usernameEditText.text.toString()
        if (usernameText == "")
        {
            return "Ingresar nombre de usuario"
        }
        return null
    }

    private fun emailFocusListener()
    {
        binding.emailEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.emailContainer.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String?
    {
        val emailText = binding.emailEditText.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
        {
            return "Dirección no válida"
        }
        return null
    }

    private fun passwordFocusListener()
    {
        binding.passwordEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.passwordContainer.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String?
    {
        val passwordText = binding.passwordEditText.text.toString()
        if(passwordText.length < 8)
        {
            return "Se requieren más de 8 caracteres"
        }
        if(!passwordText.matches(".*[A-Z].*".toRegex()))
        {
            return "Se requiere al menos 1 mayúscula"
        }
        if(!passwordText.matches(".*[a-z].*".toRegex()))
        {
            return "Se requiere al menos 1 minúscula"
        }
        if(!passwordText.matches(".*[@#\$%^&+=].*".toRegex()))
        {
            return "Se requiere al menos unn caracter especial (@#\$%^&+=)"
        }

        return null
    }

    private fun companynameFocusListener()
    {
        binding.companynameEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.companynameContainer.helperText = validCompanyname()
            }
        }
    }

    private fun validCompanyname(): String?
    {
        val companynameText = binding.companynameEditText.text.toString()
        if (companynameText == "") {
            return "Ingresar nombre de empresa"
        }
        return null
    }

    private fun companycodeFocusListener()
    {
        binding.companycodeEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.companycodeContainer.helperText = validCompanycode()
            }
        }
    }

    private fun validCompanycode(): String?
    {
        val companycodeText = binding.companycodeEditText.text.toString()
        if (companycodeText == "") {
            return "Ingresar clave de empresa"
        }
        return null
    }
}