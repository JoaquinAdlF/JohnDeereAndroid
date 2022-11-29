package com.example.johndeere.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.johndeere.R
import com.example.johndeere.api.ApiService
import com.example.johndeere.databinding.FragmentQuizEndPageBinding
import com.example.johndeere.models.progress

/*
Fragmento para la finalización de un quiz.
 */

class QuizEndPage : Fragment() {
    private var _binding: FragmentQuizEndPageBinding? = null
    private val binding get() = _binding!!
    private var score = 0
    private var totalQuestions = 0
    private var difficulty: String? = null
    private var level = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentQuizEndPageBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val backToQR = view.findViewById<Button>(R.id.backToQuizRoad)

        // Recibe como parámetro la cantidad total de preguntas y el puntaje obtenido para desplegar en pantalla
        arguments?.let {
            score = it.getInt("correct")
            totalQuestions = it.getInt("questions")
            difficulty = it.getString("difficulty") as String
            level = it.getInt("level")
            binding.quizEndPageTextView.text = getString(R.string.finalScore, score, totalQuestions)
        }

        // Guarda la dificultad y crea una variable para poder verificar si es un usuario registrado
        if ((score % totalQuestions) == 0 && score != 0){
            val sharedPref = activity?.getSharedPreferences(difficulty,Context.MODE_PRIVATE)
            val sharedPrefProfile = activity?.getSharedPreferences("pref",Context.MODE_PRIVATE)
            var isGuest: String? = ""
            var difficultyLevel: String? = null

            if (sharedPrefProfile?.getString("username","") == ""){
                isGuest = "guest"
            }

            when (difficulty) {
                "Dificultad básica" -> {
                    difficultyLevel = "level1$isGuest"
                }
                "Dificultad intermedia" -> {
                    difficultyLevel = "level2$isGuest"
                }
                "Dificultad avanzada" -> {
                    difficultyLevel = "level3$isGuest"
                }
            }

            val progress = sharedPrefProfile?.getFloat(difficultyLevel, 0f)

            updatePreferences(progress, isGuest, difficultyLevel, sharedPref, sharedPrefProfile)
        }

        // Volver directamente al fragmento de Quiz Road
        backToQR.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("difficulty", difficulty)
            view.findNavController().navigate(R.id.action_quizEndPage_frag_to_quizRoad_frag, bundle)
        }
    }

    // Metodo para guardar el progreso en preferencias
    private fun updatePreferences(progress: Float?, isGuest: String?, difficultyLevel: String?, sharedPref: SharedPreferences?, sharedPrefProfile: SharedPreferences?){
        val editor = sharedPref?.edit()
        val editorProgress = sharedPrefProfile?.edit()
        val newProgress: Float

        if (level == 1){
            if (progress == 0f){
                newProgress = progress.plus(20f)
                editor?.putBoolean("level2$isGuest", true)
                editorProgress?.putFloat(difficultyLevel, newProgress)
            }
        }
        else if (level == 2){
            if (progress == 20f) {
                newProgress = progress.plus(20f)
                editor?.putBoolean("level3$isGuest", true)
                editorProgress?.putFloat(difficultyLevel, newProgress)
            }
        }
        else if (level == 3){
            if (progress == 40f){
                newProgress = progress.plus(20f)
                editor?.putBoolean("level4$isGuest", true)
                editorProgress?.putFloat(difficultyLevel, newProgress)
            }
        }
        else if (level == 4){
            if (progress == 60f){
                newProgress = progress.plus(20f)
                editor?.putBoolean("level5$isGuest", true)
                editorProgress?.putFloat(difficultyLevel, newProgress)
            }
        }
        else if (level == 5){
            if (progress == 80f){
                newProgress = progress.plus(20f)
                editorProgress?.putFloat(difficultyLevel, newProgress)
            }
        }
        editor?.apply()
        editorProgress?.apply()

        // Verificar si es un usuario registrado o un invitado
        if (isGuest != "guest") {
            if (checkForInternet(this.requireActivity())) {
                sendForm()
            } else {
                AlertDialog.Builder(context)
                    .setTitle("No hay conexión a internet, no se pudo actualizar el progreso")
                    .setPositiveButton("Ok") { _, _ ->
                    }
                    .show()
            }
        }
    }

    // Metodo para verificar si hay conexion a internet
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

    // Metodo para actualizar el progreso del empleado en la base de datos
    private fun sendForm() {
        val sharedPrefProfile = activity?.getSharedPreferences("pref",Context.MODE_PRIVATE)

        val id = sharedPrefProfile?.getFloat("id", 0f)
        val level1 = sharedPrefProfile?.getFloat("level1", 0f)
        val level2 = sharedPrefProfile?.getFloat("level2", 0f)
        val level3 = sharedPrefProfile?.getFloat("level3", 0f)

        val updateData = progress (
            id = 0,
            level1 = level1?.toInt(),
            level2 = level2?.toInt(),
            level3 = level3?.toInt(),
            employeeid = id?.toInt()
        )

        ApiService.updateProgress(updateData) {
            if (it?.message == "Error while getting progress") {
                AlertDialog.Builder(context)
                    .setTitle("No se pudo actualizar el progreso")
                    .setPositiveButton("Ok") { _,_ ->
                    }
                    .show()
            }
            else {
                AlertDialog.Builder(context)
                    .setTitle("Progreso actualizado")
                    .setPositiveButton("Ok") { _,_ ->
                    }
                    .show()
            }
        }
    }
}