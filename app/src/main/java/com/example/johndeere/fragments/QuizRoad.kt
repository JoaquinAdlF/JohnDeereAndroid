package com.example.johndeere.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.johndeere.GeneralCategoriesList
import com.example.johndeere.R
import com.example.johndeere.Words
import com.example.johndeere.databinding.FragmentQuizRoadBinding
import com.example.johndeere.storage.TechnicalCategoriesList
import java.util.ArrayList

/*
Fragmento para la selección de un quiz.
 */

class QuizRoad : Fragment() {
    private var _binding: FragmentQuizRoadBinding? = null
    private val binding get() = _binding!!
    private var difficulty = ""
    private var questions = 0
    private lateinit var wordsData: ArrayList<Words>
    //private lateinit var wordGroup1: ArrayList<Words>
    //private lateinit var wordGroup2: ArrayList<Words>
    //private lateinit var wordGroup3: ArrayList<Words>
    private lateinit var level1Words: ArrayList<Words>
    private lateinit var level2Words: ArrayList<Words>
    private lateinit var level3Words: ArrayList<Words>
    private lateinit var level4Words: ArrayList<Words>
    private lateinit var level5Words: ArrayList<Words>
    private lateinit var l1: Button
    private lateinit var l2: Button
    private lateinit var l3: Button
    private lateinit var l4: Button
    private lateinit var l5: Button
    private var bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentQuizRoadBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        l1 = view.findViewById(R.id.level1_button)
        l2 = view.findViewById(R.id.level2_button)
        l3 = view.findViewById(R.id.level3_button)
        l4 = view.findViewById(R.id.level4_button)
        l5 = view.findViewById(R.id.level5_button)

        // Inicializacion de las listas de arrays para poder almacenar las palabras
        wordsData = ArrayList()
        //wordGroup1 = ArrayList()
        //wordGroup2 = ArrayList()
        //wordGroup3 = ArrayList()
        level1Words = ArrayList()
        level2Words = ArrayList()
        level3Words = ArrayList()
        level4Words = ArrayList()
        level5Words = ArrayList()

        // Recolección de todas las palabras con las que cuenta la Aplicación
        for (category in GeneralCategoriesList) {
            for (word in category.subCategory) {
                wordsData.add(word)
            }
        }
        for (category in TechnicalCategoriesList) {
            for (word in category.subCategory) {
                wordsData.add(word)
            }
        }

        // Cálculo de la cantidad de palabras para poder dividirlas en partes
        val fullSize = wordsData.size
        val arraySize = fullSize / 5

        // Llenado de 5 arrays distintos para tener una lista de palabras por nivel
        for (i in 0 until fullSize) {
            when (i) {
                in 0..arraySize -> level1Words.add(wordsData[i])
                in arraySize+1..arraySize*2 -> level2Words.add(wordsData[i])
                in (arraySize*2)+1..(arraySize*3) -> level3Words.add(wordsData[i])
                in (arraySize*3)+1..(arraySize*4) -> level4Words.add(wordsData[i])
                in (arraySize*4)+1 until fullSize -> level5Words.add(wordsData[i])
            }
        }

        // Recibe como parámetro el nivel de dificultad para crear los bundles correspondientes
        arguments?.let {
            difficulty = it.getString("difficulty") as String

            // Si la dificultad es básica, se hace el setup de acuerdo a esta dificultad
            when (difficulty) {
                "Dificultad básica" -> {
                    questions = 10
                    binding.quizRoadTextView.text = difficulty
                    bundle.putString("difficulty", difficulty)
                    bundle.putInt("questions", questions)

                }
                // Si la dificultad es intermedia, se hace el setup de acuerdo a esta dificultad
                "Dificultad intermedia" -> {
                    questions = 10
                    binding.quizRoadTextView.text = difficulty
                    bundle.putString("difficulty", difficulty)
                    bundle.putInt("questions", questions)
                }
                // Si la dificultad es avanzada, se hace el setup de acuerdo a esta dificultad
                "Dificultad avanzada" -> {
                    questions = 10
                    binding.quizRoadTextView.text = difficulty
                    bundle.putString("difficulty", difficulty)
                    bundle.putInt("questions", questions)
                }
            }
        }

        getStats()

        // Llamado a la acción de acceso a los niveles y asignacion del bundle de palabras para el nivel
        l1.setOnClickListener{
            val level = 1
            bundle.putInt("level", level)
            bundle.putParcelableArrayList("words", level1Words)
            view.findNavController().navigate(R.id.action_quizRoad_frag_to_quizPage_frag, bundle)
        }

        l2.setOnClickListener{
            val level = 2
            bundle.putInt("level", level)
            bundle.putParcelableArrayList("words", level2Words)
            view.findNavController().navigate(R.id.action_quizRoad_frag_to_quizPage_frag, bundle)
        }

        l3.setOnClickListener{
            val level = 3
            bundle.putInt("level", level)
            bundle.putParcelableArrayList("words", level3Words)
            view.findNavController().navigate(R.id.action_quizRoad_frag_to_quizPage_frag, bundle)
        }

        l4.setOnClickListener{
            val level = 4
            bundle.putInt("level", level)
            bundle.putParcelableArrayList("words", level4Words)
            view.findNavController().navigate(R.id.action_quizRoad_frag_to_quizPage_frag, bundle)
        }

        l5.setOnClickListener{
            val level = 5
            bundle.putInt("level", level)
            bundle.putParcelableArrayList("words", level5Words)
            view.findNavController().navigate(R.id.action_quizRoad_frag_to_quizPage_frag, bundle)
        }
    }

    // Funcion para obtener el progreso por dificultad y configurar los niveles disponibles
    private fun getStats(){
        val sharedPrefProfile = activity?.getSharedPreferences("pref",Context.MODE_PRIVATE)
        var enablel2 = false
        var enablel3 = false
        var enablel4 = false
        var enablel5 = false
        var isGuest: String? = ""

        if (sharedPrefProfile?.getString("username","") == ""){
            isGuest = "guest"
        }

        val level1 = sharedPrefProfile?.getFloat("level1$isGuest",0f)
        val level2 = sharedPrefProfile?.getFloat("level2$isGuest",0f)
        val level3 = sharedPrefProfile?.getFloat("level3$isGuest",0f)
        val progress1 = (level1?.times(5f))?.div(100f)?.toInt()
        val progress2 = (level2?.times(5f))?.div(100f)?.toInt()
        val progress3 = (level3?.times(5f))?.div(100f)?.toInt()

        when (difficulty) {
            "Dificultad básica" -> {
                when (progress1) {
                    1 -> {
                        enablel2 = true
                    }
                    2 -> {
                        enablel2 = true
                        enablel3 = true
                    }
                    3 -> {
                        enablel2 = true
                        enablel3 = true
                        enablel4 = true
                    }
                    in 4..5 -> {
                        enablel2 = true
                        enablel3 = true
                        enablel4 = true
                        enablel5 = true
                    }
                }
            }
            "Dificultad intermedia" -> {
                when (progress2) {
                    1 -> {
                        enablel2 = true
                    }
                    2 -> {
                        enablel2 = true
                        enablel3 = true
                    }
                    3 -> {
                        enablel2 = true
                        enablel3 = true
                        enablel4 = true
                    }
                    in 4..5 -> {
                        enablel2 = true
                        enablel3 = true
                        enablel4 = true
                        enablel5 = true
                    }
                }
            }
            "Dificultad avanzada" -> {
                when (progress3) {
                    1 -> {
                        enablel2 = true
                    }
                    2 -> {
                        enablel2 = true
                        enablel3 = true
                    }
                    3 -> {
                        enablel2 = true
                        enablel3 = true
                        enablel4 = true
                    }
                    in 4..5 -> {
                        enablel2 = true
                        enablel3 = true
                        enablel4 = true
                        enablel5 = true
                    }
                }
            }
        }

        l2.isEnabled = enablel2
        l3.isEnabled = enablel3
        l4.isEnabled = enablel4
        l5.isEnabled = enablel5
    }
}