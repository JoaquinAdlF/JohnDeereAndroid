package com.example.johndeere.fragments

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.johndeere.R
import com.example.johndeere.api.ApiService
import com.example.johndeere.databinding.FragmentLogInBinding
import com.example.johndeere.models.employeeInfo

/*
Fragmento de inicio de sesión del usuario.
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

    override fun onResume() {
        super.onResume()
        binding.logpbarContainer.visibility = View.GONE
        binding.logInButton.visibility = View.VISIBLE
        val sharedPref = this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "")
        if (username != "") {
            view?.findNavController()?.navigate(R.id.action_logIn_frag_to_mainPage_frag)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logpbarContainer.visibility = View.GONE

        usernameFocusListener()
        passwordFocusListener()

        binding.logInButton.setOnClickListener {
            binding.logInButton.visibility = View.GONE
            binding.logpbarContainer.visibility = View.VISIBLE
            submitForm()
        }

    }


    private fun submitForm() {
        binding.loginusernameContainer.helperText = validUsername()
        binding.loginpasswordContainer.helperText = validPassword()

        val validUsername = binding.loginusernameContainer.helperText == null
        val validPassword = binding.loginpasswordContainer.helperText == null

        if (validUsername && validPassword) {
            if (checkForInternet(this.requireActivity())) {
                sendForm()
            } else {
                AlertDialog.Builder(context)
                    .setTitle("No hay conexión a internet")
                    .setPositiveButton("Ok") { _, _ ->
                        binding.logInButton.visibility = View.VISIBLE
                        binding.logpbarContainer.visibility = View.GONE
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

    private fun invalidForm() {
        var message = ""
        if (binding.loginusernameContainer.helperText != null)
            message += "\n\nNombre de usuario: " + binding.loginusernameContainer.helperText
        if (binding.loginpasswordContainer.helperText != null)
            message += "\n\nContraseña: " + binding.loginpasswordContainer.helperText

        AlertDialog.Builder(context)
            .setTitle("Datos no válidos")
            .setMessage(message)
            .setPositiveButton("Ok"){ _,_ ->
                binding.logInButton.visibility = View.VISIBLE
                binding.logpbarContainer.visibility = View.GONE
            }
            .show()
    }

    private fun sendForm() {
        val employeeData = employeeInfo (
            id = 0,
            username = binding.loginusernameEditText.text.toString(),
            email = "",
            password = binding.loginpasswordEditText.text.toString(),
            companyname = "",
            companycode = "",
            organizationid = 0,
            level1 = 0,
            level2 = 0,
            level3 = 0,
            message = ""
        )

        ApiService.login(employeeData) {
            if (it?.get(0)?.message == "User not found") {
                AlertDialog.Builder(context)
                    .setTitle("Usuario no encontrado")
                    .setPositiveButton("Ok") { _,_ ->
                        binding.logInButton.visibility = View.VISIBLE
                        binding.logpbarContainer.visibility = View.GONE
                    }
                    .show()
            }
            else if (it?.get(0)?.message == "Not allowed") {
                AlertDialog.Builder(context)
                    .setTitle("Contraseña incorrecta")
                    .setPositiveButton("Ok") { _,_ ->
                        binding.logInButton.visibility = View.VISIBLE
                        binding.logpbarContainer.visibility = View.GONE
                    }
                    .show()
            }
            else if (it?.get(0)?.id!! > 0) {
                savePreferences(it[0])
                AlertDialog.Builder(context)
                    .setTitle("Bienvenido(a) " + it[0].username)
                    .setPositiveButton("Ok") { _,_ ->
                        view?.findNavController()?.navigate(R.id.action_logIn_frag_to_mainPage_frag)
                    }
                    .show()
            }
        }
    }

    private fun usernameFocusListener()
    {
        binding.loginusernameEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.loginusernameContainer.helperText = validUsername()
            }
        }
    }

    private fun validUsername(): String?
    {
        val usernameText = binding.loginusernameEditText.text.toString()
        if (usernameText == "")
        {
            return "Ingresar nombre de usuario"
        }
        return null
    }

    private fun passwordFocusListener()
    {
        binding.loginpasswordEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.loginpasswordContainer.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String?
    {
        val passwordText = binding.loginpasswordEditText.text.toString()
        if (passwordText == "")
        {
            return "Ingresar contraseña"
        }
        return null
    }

    private fun savePreferences(employeeInfos: employeeInfo) {
        val preferences = this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        with(preferences.edit()) {
            putFloat("id", employeeInfos.id!!.toFloat())
            putString("username", employeeInfos.username!!.toString())
            //putFloat("organizationid", employeeInfos.organizationid!!.toFloat())
            putFloat("level1", employeeInfos.level1!!.toFloat())
            putFloat("level2", employeeInfos.level2!!.toFloat())
            putFloat("level3", employeeInfos.level3!!.toFloat())
            apply()
        }
    }
}