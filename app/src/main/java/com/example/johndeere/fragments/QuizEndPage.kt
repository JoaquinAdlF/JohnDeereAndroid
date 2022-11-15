package com.example.johndeere.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.johndeere.R
import com.example.johndeere.databinding.FragmentQuizEndPageBinding

/*
Fragmento para la finalización de un quiz.
 */

class QuizEndPage : Fragment() {
    private var _binding: FragmentQuizEndPageBinding? = null
    private val binding get() = _binding!!
    private var score = 0
    private var totalQuestions = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentQuizEndPageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recibe como parámetro la cantidad total de preguntas y el puntaje obtenido para desplegar en pantalla
        arguments?.let {
            score = it.get("correct") as Int
            totalQuestions = it.get("questions") as Int

            binding.quizEndPageTextView.text = getString(R.string.finalScore, score, totalQuestions)
        }
    }
}