package com.example.johndeere

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.johndeere.databinding.FragmentWordsBinding

class Words : Fragment() {
    private var _binding: FragmentWordsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWordsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val w1 = view.findViewById<Button>(R.id.word1_button)
        val w2 = view.findViewById<Button>(R.id.word2_button)
        val w3 = view.findViewById<Button>(R.id.word3_button)

        w1.setOnClickListener{
            view.findNavController().navigate(R.id.action_words_frag_to_video_frag)
        }

        w2.setOnClickListener{
            view.findNavController().navigate(R.id.action_words_frag_to_video_frag)
        }

        w3.setOnClickListener{
            view.findNavController().navigate(R.id.action_words_frag_to_video_frag)
        }
    }
}