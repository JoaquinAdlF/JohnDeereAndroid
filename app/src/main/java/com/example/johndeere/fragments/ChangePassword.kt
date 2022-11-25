package com.example.johndeere.fragments

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.johndeere.R
import com.example.johndeere.api.ApiService
import com.example.johndeere.databinding.FragmentChangePasswordBinding
import com.example.johndeere.models.updatePasswordInfo

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
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.changepbarContainer.visibility = View.GONE
        binding.changePassButton.visibility = View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        oldpasswordFocusListener()
        newpasswordFocusListener()

        binding.changePassButton.setOnClickListener {
            submitForm()
            binding.changepbarContainer.visibility = View.VISIBLE
            binding.changePassButton.visibility = View.GONE
        }

    }

    private fun submitForm() {
        binding.oldpasswordContainer.helperText = validoldPassword()
        binding.newpasswordContainer.helperText = validnewPassword()

        val validoldPassword = binding.oldpasswordContainer.helperText == null
        val validnewPassword = binding.newpasswordContainer.helperText == null

        if (validoldPassword && validnewPassword) {
            if (checkForInternet(this.requireActivity())) {
                sendForm()
            } else {
                AlertDialog.Builder(context)
                    .setTitle("No hay conexión a internet")
                    .setPositiveButton("Ok") { _, _ ->
                        binding.changepbarContainer.visibility = View.GONE
                        binding.changePassButton.visibility = View.VISIBLE
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
        if (binding.oldpasswordContainer.helperText != null)
            message += "\n\nContraseña anterior: " + binding.oldpasswordContainer.helperText
        if (binding.newpasswordContainer.helperText != null)
            message += "\n\nContraseña nueva: " + binding.newpasswordContainer.helperText

        AlertDialog.Builder(context)
            .setTitle("Datos no válidos")
            .setMessage(message)
            .setPositiveButton("Ok"){ _,_ ->
                binding.changepbarContainer.visibility = View.GONE
                binding.changePassButton.visibility = View.VISIBLE
            }
            .show()
    }

    private fun sendForm()
    {
        val sharedPref = this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)

        val username = sharedPref.getString("username", "")
        val id = sharedPref.getFloat("id", 0f)

        val updateData = updatePasswordInfo (
            id = id.toInt(),
            username = username,
            oldPassword = binding.oldpasswordEditText.text.toString(),
            newPassword = binding.newpasswordEditText.text.toString(),
            affectedRows = 0,
            message = ""
                )

        ApiService.updatePassword(updateData) {
            if (it?.get(0)?.message == "Not allowed") {
                AlertDialog.Builder(context)
                    .setTitle("Contraseña anterior incorrecta")
                    .setPositiveButton("Ok") { _,_ ->
                        binding.changepbarContainer.visibility = View.GONE
                        binding.changePassButton.visibility = View.VISIBLE
                    }
                    .show()
            }
            else if (it?.get(0)?.message == "Updated successfuly") {
                AlertDialog.Builder(context)
                    .setTitle("Contraseña actualizada")
                    .setPositiveButton("Ok") { _,_ ->
                        view?.findNavController()?.navigate(R.id.action_changePassword_frag_to_profile_frag)
                    }
                    .show()
            }
        }
    }

    private fun oldpasswordFocusListener()
    {
        binding.oldpasswordEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.oldpasswordContainer.helperText = validoldPassword()
            }
        }
    }

    private fun validoldPassword(): String?
    {
        val oldpasswordText = binding.oldpasswordEditText.text.toString()
        if (oldpasswordText == "")
        {
            return "Ingresar contraseña anterior"
        }
        return null
    }

    private fun newpasswordFocusListener()
    {
        binding.newpasswordEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.newpasswordContainer.helperText = validnewPassword()
            }
        }
    }

    private fun validnewPassword(): String?
    {
        val passwordText = binding.newpasswordEditText.text.toString()
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
}