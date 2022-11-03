package com.example.johndeere

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.johndeere.databinding.FragmentQuizzesBinding

class Quizzes : Fragment() {
    private var _binding: FragmentQuizzesBinding ? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentQuizzesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val basic = view.findViewById<Button>(R.id.basic_button)
        val intermediate = view.findViewById<Button>(R.id.intermediate_button)
        val advanced = view.findViewById<Button>(R.id.advanced_button)

        basic.setOnClickListener{
            view.findNavController().navigate(R.id.action_quizzes_frag_to_quizRoad_frag)
        }

        intermediate.setOnClickListener{
            view.findNavController().navigate(R.id.action_quizzes_frag_to_quizRoad_frag)
        }

        advanced.setOnClickListener{
            view.findNavController().navigate(R.id.action_quizzes_frag_to_quizRoad_frag)
        }
    }
}