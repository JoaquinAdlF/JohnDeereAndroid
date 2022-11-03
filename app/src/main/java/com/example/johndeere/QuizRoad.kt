package com.example.johndeere

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.johndeere.databinding.FragmentQuizRoadBinding

class QuizRoad : Fragment() {
    private var _binding: FragmentQuizRoadBinding? = null
    private val binding get() = _binding!!

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
        val l1 = view.findViewById<Button>(R.id.level1_button)
        val l2 = view.findViewById<Button>(R.id.level2_button)
        val l3 = view.findViewById<Button>(R.id.level3_button)
        val l4 = view.findViewById<Button>(R.id.level4_button)
        val l5 = view.findViewById<Button>(R.id.level5_button)

        l1.setOnClickListener{
            view.findNavController().navigate(R.id.action_quizRoad_frag_to_quizPage_frag)
        }

        l2.setOnClickListener{
            view.findNavController().navigate(R.id.action_quizRoad_frag_to_quizPage_frag)
        }

        l3.setOnClickListener{
            view.findNavController().navigate(R.id.action_quizRoad_frag_to_quizPage_frag)
        }

        l4.setOnClickListener{
            view.findNavController().navigate(R.id.action_quizRoad_frag_to_quizPage_frag)
        }

        l5.setOnClickListener{
            view.findNavController().navigate(R.id.action_quizRoad_frag_to_quizPage_frag)
        }
    }
}