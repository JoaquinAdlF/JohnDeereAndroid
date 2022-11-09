package com.example.johndeere

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.johndeere.databinding.FragmentWordsBinding
import com.example.johndeere.databinding.FragmentCategoriesBinding

class WordsFragment : Fragment() {
    private var _binding: FragmentWordsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val category = it.get("category") as Categories
            val adapterCategory = adapterSubCategories(requireActivity(), category.subCategory) {
                val bundle = Bundle()
                bundle.putParcelable("word", it)
                Navigation.findNavController(view).navigate(R.id.action_words_frag_to_video_frag, bundle)
            }

            binding.rvWords.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
            binding.rvWords.adapter = adapterCategory
            binding.rvWords.layoutManager = LinearLayoutManager(requireActivity())
        }

        /*
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

         */
    }
}